package com.fy.catchdoll.presentation.presenter.msg;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.fy.catchdoll.library.utils.Device;
import com.fy.catchdoll.library.utils.JsonUtil;
import com.fy.catchdoll.library.utils.JsonUtils;
import com.fy.catchdoll.presentation.application.CdApplication;
import com.fy.catchdoll.presentation.model.business.msg.JavaWebSBiz;
import com.fy.catchdoll.presentation.model.business.msg.UrlBiz;
import com.fy.catchdoll.presentation.model.business.msg.inter.IMessageBiz;
import com.fy.catchdoll.presentation.model.business.msg.inter.OnReLinkSocketListener;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.msg.MessDto;
import com.fy.catchdoll.presentation.model.dto.msg.SentMessDto;
import com.fy.catchdoll.presentation.model.dto.msg.SocketUrlDto;
import com.fy.catchdoll.presentation.presenter.BaseCallbackPresenter;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.account.ILoginChangeListener;
import com.fy.catchdoll.presentation.presenter.account.LoginNotifyManager;
import com.fy.catchdoll.presentation.view.adapters.wrap.WrapConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xky on 16/6/21.
 */
public class DmPresenter implements OnReLinkSocketListener, ILoginChangeListener {
    private Context mContext;
    private UrlBiz mUrlBiz = null;
    private IMessageBiz mMegBiz = null;
    private boolean isInit = false;
    private String DM_LOGIN_KEY = "dm_login_key";
    private List<MessDto> msgs = new ArrayList<MessDto>();

    Random random = new Random(System.nanoTime());
    private Handler mHandler = new Handler();
    private static DmPresenter INSTANCE;
    private SocketUrlDto urlDto;
    private boolean isDestory;
    private String roomId;

    private DmPresenter() {
        mContext = CdApplication.getApplication();
    }

    public static DmPresenter getInstance() {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                synchronized (DmPresenter.class) {
                    INSTANCE = new DmPresenter();
                }
            }
        }
        return INSTANCE;
    }


    private void setListener() {
        mUrlBiz.setOnBizCalLBack(new OnUrlBizCallBack());
        mMegBiz.setOnBizCalLBack(new OnMegBizCalLback());
        mMegBiz.setOnReLinkSocketListener(this);
        LoginNotifyManager.getInstance().registLoginChangeCallBack(DM_LOGIN_KEY, this);
    }

    public DmPresenter init() {
        isInit = false;
        isDestory = false;
        initBiz();
        setListener();
        return this;
    }


    /**
     * 加载数据
     */
    public DmPresenter loadUrl() {
        if (mUrlBiz != null) {
            mUrlBiz.loadSocketUrl(this.roomId);
        }
        return this;
    }

    /**
     * 发送弹幕
     *
     * @param content
     */
    public void sentMessage(String content) {
        boolean checkEv = checkEnvironment();
        if (checkEv) {
            //检测通过可以发送弹幕
            SentMessDto dto = generateCommonDto(content);
            if (mMegBiz == null) {
                init();
                loadUrl();
                return;
            }
            mMegBiz.sentMeg(dto);
        }
    }

//    private SentMessDto generateCommonDto(String content) {
//        User user = AccountManager.getInstance().getUser();
//
//        MessDto dto = new MessDto();
//        dto.setType(MessDto.NORMAL);
//        dto.setUser_id(user.getId());
//        dto.setNickname(user.getNickname());
//        dto.setHeadimgurl(user.getHeadimgurl());
//        dto.setWawacontent(content);
//
//        String msgContent = JsonUtils.Obj2Json(dto);
//        SentMessDto sendMsg = new SentMessDto();
//
//        sendMsg.setType(MessDto.NORMAL);
//        sendMsg.setContent("wawa");
//        sendMsg.setWawacontent(msgContent);
//
//        if (urlDto != null){
//            sendMsg.setUser_id(urlDto.getUser_id_prefix()+user.getId());
//            sendMsg.setUser_nick(user.getNickname());
//        }
//        return sendMsg;
//    }

    private SentMessDto generateCommonDto(String content) {
        User user = AccountManager.getInstance().getUser();
        SentMessDto dto = new SentMessDto();

        dto.setType(MessDto.NORMAL);
        dto.setWaka_type(MessDto.NORMAL);
        dto.setIs_mobile("1");
        if (urlDto != null){
            dto.setUser_id(urlDto.getUser_id_prefix()+user.getId());
            dto.setUser_nick(user.getNickname());
        }
        dto.setUser_avatar(user.getHeadimgurl());
        dto.setContent(content);
        return dto;
    }

    private boolean checkEnvironment() {
        User user = AccountManager.getInstance().getUser();
        if (user == null) {
            Toast.makeText(mContext, "还未登录哦", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Device.isOnline(mContext)) {
            Toast.makeText(mContext, "无网络不能发送弹幕", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onReLink() {
        int time = random.nextInt(10);
        Log.i("test", (time + 10) + "秒后弹幕重连");
        mHandler.removeCallbacks(mLinkTask);
        mHandler.postDelayed(mLinkTask,(time+10)*1000);
    }

    private Runnable mLinkTask = new Runnable() {
        public void run() {
            loadUrl();
        }
    };

    /**
     * 消费数据
     */
    public void desotryData() {
        if (mMegBiz != null) {
            mMegBiz.destroySocket();
        }
        isDestory = true;
        mHandler.removeCallbacks(mLinkTask);
        LoginNotifyManager.getInstance().unRegisteLoginChangeCallBack(DM_LOGIN_KEY);
    }

    @Override
    public void onLoginChange() {
        loadUrl();
        if (msgs != null) {
            msgs.clear();
            notifyMegsFilterByBan();
        }
    }

    /**
     * 连接弹幕
     * @param urlDto
     */
    public void linkDanmu(SocketUrlDto urlDto,String roomId){
        this.urlDto = urlDto;
        this.roomId = roomId;
        if (mMegBiz != null){
            Log.i("test", "---弹幕--请求弹幕地址成功");
            mMegBiz.changeInit(urlDto.getWsurl());
        }
    }

    class OnUrlBizCallBack implements BaseCallbackPresenter {

        @Override
        public boolean callbackResult(Object obj) {
            if (isDestory){
                return false;
            }
            isInit = true;
            if (obj != null && obj instanceof SocketUrlDto) {
                SocketUrlDto url = (SocketUrlDto) obj;
                Log.i("test", "---弹幕--请求弹幕地址成功");
                mMegBiz.changeInit(url.getWsurl());
            } else {
                Log.i("test", "-----弹幕----请求弹幕地址失败");
                onReLink();
            }
            return false;
        }

        @Override
        public void onErrer(int code, String msg) {
            if (isDestory) {
                return;
            }
            isInit = true;
            Log.i("test", "-------弹幕------请求弹幕地址失败");
            onReLink();
        }
    }

    class OnMegBizCalLback implements BaseCallbackPresenter {

        @Override
        public boolean callbackResult(final Object obj) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (obj != null && obj instanceof MessDto) {
                        MessDto dto = (MessDto) obj;
                        notifyMeg((MessDto) dto);
                    }
                }
            });
            return false;
        }

        @Override
        public void onErrer(int code, String msg) {

        }
    }



    public void notifyMeg(MessDto dto) {
        if (dto != null) {
            if (checkMessageType(dto) && checkMessageSelfType(dto)) {
                if (!operateMegType(dto)) {
                    return;
                }
//                Toast.makeText(mContext,"-----"+dto.getType(),Toast.LENGTH_LONG).show();
//                Log.i("test","_type__"+dto.getType()+"_content_"+dto.getContent()+"___"+dto.toString());
                if (dto.getType() == MessDto.NORMAL){
                    String content = dto.getContent();
                    if (!TextUtils.isEmpty(content) && content.length()>50){
                        dto.setContent(content.substring(0,50));
                    }
                }
                msgs.add(dto);
                MessageNotifyManager.getInstance().notfifyGiftCame(dto);
            }
        }
    }

    private boolean checkMessageSelfType(MessDto dto) {
        return (dto.getWaka_type() == MessDto.WAWA_MSG
                || dto.getWaka_type() == MessDto.WAWA_ENTER_ROOM
                || dto.getWaka_type() == MessDto.WAWA_MACHINE_STATE_BUSY
                || dto.getWaka_type() == MessDto.WAWA_MACHINE_STATE_FREE
                || dto.getWaka_type() == MessDto.WAWA_MACHINE_STATE_FINISH
        );
    }

    public void notifyMegsFilterByBan() {
        List<MessDto> msgs = getFilterMesg();
        MessageNotifyManager.getInstance().notifyGiftCames(msgs);
    }

    public void clearCacheMegs() {
        if (msgs != null && msgs.size() > 0) {
            msgs.clear();
            MessageNotifyManager.getInstance().notifyGiftCames(msgs);
        }
    }

    private List<MessDto> getFilterMesg() {
        List<MessDto> filterMsg = new ArrayList<MessDto>();
        for (int i = 0; i < msgs.size(); i++) {
            MessDto dto = msgs.get(i);
        }
        return filterMsg;
    }

    private boolean operateMegType(MessDto dto) {
        boolean isUser = false;
        switch (dto.getType()) {
            case MessDto.NORMAL:
                dto.setItemType(WrapConstants.WRAP_MAIN_COMMON_MSG);
                isUser = true;
                break;
        }
        return isUser;
    }


    /**
     * 区分飞云
     * @param dto
     * @return
     */
    private boolean checkMessageType(MessDto dto) {
        return (dto.getType() == MessDto.NORMAL
        );

    }


    private void initBiz() {
        if (mMegBiz != null) {
            mMegBiz.destroySocket();
        }
        mUrlBiz = new UrlBiz(mContext);
//        if (Device.getSysVersion() == Build.VERSION_CODES.KITKAT) {
//            mMegBiz = new JavaWebSBiz(mContext);
//        } else {
//            mMegBiz = new MegBiz(mContext);
//        }
//        mMegBiz = Paths.ISTEST ? new JavaWebSBiz(mContext) : new SocIoMsg(mContext);
//          mMegBiz = new SocIoMsg(mContext);
        mMegBiz = new JavaWebSBiz(mContext);
    }

    /**
     * 生成一个系统消息 利用SYSTEMBAN
     * @param msg
     */
    public void generateOneMessage(String msg){
        if (!TextUtils.isEmpty(msg)){
            MessDto dto = new MessDto();
            dto.setContent(msg);
            dto.setType(MessDto.NORMAL);
            notifyMeg(dto);
        }
    }

}
