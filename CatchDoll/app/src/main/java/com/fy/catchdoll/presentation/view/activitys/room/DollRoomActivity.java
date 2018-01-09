package com.fy.catchdoll.presentation.view.activitys.room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.library.utils.tool.SoftKeyBoardListener;
import com.fy.catchdoll.library.widgets.CustomRecentView;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.module.support.agora.common.Constant;
import com.fy.catchdoll.module.support.agora.model.AGEventHandler;
import com.fy.catchdoll.module.support.agora.model.ConstantApp;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.room.CatchRecord;
import com.fy.catchdoll.presentation.model.dto.room.EnterRoomDto;
import com.fy.catchdoll.presentation.model.dto.room.RoomInfo;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.room.RoomPresenter;
import com.fy.catchdoll.presentation.presenter.room.VoicePresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Created by xky on 2017/12/1 0001.
 */
public class DollRoomActivity extends AppCompatBaseActivity implements AGEventHandler {
    private static final String TAG = "DollRoomActivity";
    public static final String DOLL_ROOM_KEY = "doll_room_key";
    private LinearLayout mVideoChatContainer;
    private View mChatBtn;
    private Handler handler;
    private DialogManager dialogManager;
    private CustomRecentView mRecentDoll;
    private CustomRecentView mDollShow;
    private VoicePresenter mVoicePresenter;
    private RoomPresenter mRoomPresenter;
    private View mRoomStateStartWaitContainer;
    private View mRoomStatePlayContainer;
    private View mStartCatchContainer;
    private final SparseBooleanArray mUidList = new SparseBooleanArray();
    private NetStateView mNetView;
    private String roomId;
    private EnterRoomDto mRoomDto;

    private View mCatchHistoryContainer;
    private View mDollListContainer;
    private TextView mPayGoldTv;
    private TextView mTotalGoldTv;


    @Override
    public int getLayoutId() {
        return R.layout.activity_room_2;
    }

    @Override
    public void initView() {
        mVideoChatContainer = (LinearLayout) findViewById(R.id.room_video_chat_container);
        mChatBtn = findViewById(R.id.room_chat_btn);
        mRecentDoll = (CustomRecentView) findViewById(R.id.room_recent_doll);
        mDollShow = (CustomRecentView) findViewById(R.id.room_doll_show);
        findViewById(R.id.nav_back).setOnClickListener(this);
        mRoomStateStartWaitContainer = findViewById(R.id.room_catchstate_start_container);
        mRoomStatePlayContainer = findViewById(R.id.room_catchstate_play_container);
        mStartCatchContainer = findViewById(R.id.room_state_start_wait);
        mNetView = (NetStateView) findViewById(R.id.netstate);
        mNetView.setContentView(findViewById(R.id.room_sv));
        mTotalGoldTv = (TextView) findViewById(R.id.room_gold_num);
        mPayGoldTv = (TextView) findViewById(R.id.pay_gold_tv);
        mCatchHistoryContainer = findViewById(R.id.catch_history_container);
        mDollListContainer = findViewById(R.id.roll_doll_container);
    }

    @Override
    public void initData() {
        inittopViewH();
        initIntent();
        handler = new Handler();
        dialogManager = new DialogManager(this);
        mVoicePresenter = new VoicePresenter(this);
        mRoomPresenter = new RoomPresenter();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null){
            roomId = intent.getStringExtra(DOLL_ROOM_KEY);
        }
    }

    private void initAgora(String  channel_id) {
        event().addEventHandler(this);
        int cRole = Constants.CLIENT_ROLE_AUDIENCE;
        String roomName = channel_id;
        doConfigEngine(cRole);
        worker().joinChannel(roomName, config().mUid);
    }

    /**
     * 配置分辨率
     * @param cRole
     */
    private void doConfigEngine(int cRole) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int prefIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, ConstantApp.DEFAULT_PROFILE_IDX);
        if (prefIndex > ConstantApp.VIDEO_PROFILES.length - 1) {
            prefIndex = ConstantApp.DEFAULT_PROFILE_IDX;
        }
        int vProfile = ConstantApp.VIDEO_PROFILES[prefIndex];
        worker().configEngine(cRole, vProfile);
    }


    private void inittopViewH(){
        int[] screenWidthAndHeight = DeviceUtils.getScreenWidthAndHeight(this);
        int allH = (int) (screenWidthAndHeight[1]-getResources().getDimension(R.dimen.dimen_nav_h));
        int statusBarHeight = DeviceUtils.getStatusBarHeight2(this);
        allH -=statusBarHeight;
        ViewGroup.LayoutParams params = mVideoChatContainer.getLayoutParams();
        params.height = allH;
        mVideoChatContainer.setLayoutParams(params);
    }

    @Override
    public void setListener() {
        mChatBtn.setOnClickListener(this);
        mStartCatchContainer.setOnClickListener(this);
        softKeyboardListnenr();
        mRoomPresenter.registPresenterCallBack(mEnterCallBack);
    }

    private IBasePresenterLinstener mEnterCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (obj != null){
                EnterRoomDto roomDto = (EnterRoomDto) obj;
                parseRoomData(roomDto);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            ErrorCodeOperate.executeError(TAG, getContext(), code, msg, true);
        }
    };

    private void parseRoomData(EnterRoomDto roomDto) {
        mNetView.show(NetStateView.NetState.CONTENT);
        RoomInfo machine = roomDto.getMachine();
        if (machine != null){
            initAgora(machine.getChannel_id());
        }
        initNetData(roomDto);
    }

    private void initNetData(EnterRoomDto roomDto) {
        RoomInfo machine = roomDto.getMachine();
        User user = roomDto.getUser();
        List<CatchRecord> grab_record = roomDto.getGrab_record();

        if (machine != null && user != null){
            mPayGoldTv.setText(getResources().getString(R.string.string_room_pay_gold,machine.getGold()));
            mTotalGoldTv.setText(user.getGold()+"");
        }
        if (grab_record == null || grab_record.size() == 0){
            mCatchHistoryContainer.setVisibility(View.GONE);
        }else {
            mCatchHistoryContainer.setVisibility(View.VISIBLE);
            mRecentDoll.initRecentCatchHistory(grab_record);
        }
        mDollListContainer.setVisibility(View.GONE);
    }

    @Override
    public void loadData() {
//        testData();
        mNetView.show(NetStateView.NetState.LOADING);
        mRoomPresenter.enterRoom(roomId);
    }

    private void testData(){
        List<Object> objects = new ArrayList<>();
        List<Object> objects2 = new ArrayList<>();
        for (int i=0;i<5;i++){
            objects.add("ddd");
            objects2.add("ddd");
        }
//        mRecentDoll.initRecentCatchHistory(objects);
        mDollShow.initDollShow(objects2);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.room_chat_btn:
                dialogManager.showDialog(DialogStyle.SENT_CHAT,null,null);
                break;
            case R.id.nav_back:
                finish();
                break;
            case R.id.room_state_start_wait:
                operatePlay();
                break;
        }
    }

    /**
     * 启动状态---->操作状态
     */
    private void operatePlay() {
        mRoomStateStartWaitContainer.setVisibility(View.GONE);
        mRoomStatePlayContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 等待状态--->启动状态
     */
    private void operateStart(){

    }


    @Override
    public void onResume() {
        super.onResume();
        mVoicePresenter.startPlayBgVoice();
    }

    @Override
    public void onPause() {
        super.onPause();
        mVoicePresenter.pausePlayBgVoice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVoicePresenter.endPlayBgVoice();
        doLeaveChannel();
        event().removeEventHandler(this);
    }

    private void doLeaveChannel() {
        worker().leaveChannel(config().mChannel);
    }


    /**
     * 软键盘显示与隐藏的监听
     */
    private void softKeyboardListnenr() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {/*软键盘显示：执行隐藏title动画，并修改listview高度和装载礼物容器的高度*/
            }

            @Override
            public void keyBoardHide(int height) {/*软键盘隐藏：隐藏聊天输入框并显示聊天按钮，执行显示title动画，并修改listview高度和装载礼物容器的高度*/
                dialogManager.dismissDialog();
            }
        });
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        doRenderRemoteUi(uid);
    }

    @Override
    public void onJoinChannelSuccess(final String channel, final int uid, final int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                worker().getEngineConfig().mUid = uid;
            }
        });
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        doRemoveRemoteUi(uid);
        mUidList.delete(uid);
    }

    private void doRemoveRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                rtcEngine().setupRemoteVideo(new VideoCanvas(null, VideoCanvas.RENDER_MODE_HIDDEN, uid));
            }
        });
    }

    private void doRenderRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                if (uid != Constant.Wawaji_CAM_MAIN && uid != Constant.Wawaji_CAM_SECONDARY) {
                    return;
                }
                boolean isMain = uid == Constant.Wawaji_CAM_MAIN;
                if (isMain) { // always be the main cam
                    doSetupVideoStreamView(uid);
                }
                mUidList.put(uid, isMain);
            }
        });
    }

    private void doSetupVideoStreamView(int uid) {
        SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
        surfaceV.setZOrderOnTop(true);
        surfaceV.setZOrderMediaOverlay(true);
        if (config().mWawajiUid == uid) {
            return;
        } else {
            rtcEngine().setupRemoteVideo(new VideoCanvas(surfaceV, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        }

        FrameLayout container = (FrameLayout) findViewById(R.id.gaming_video);
        if (container.getChildCount() >= 2) {
            return;
        }
        container.removeAllViews();
        container.addView(surfaceV);

        config().mWawajiUid = uid;
    }

    public void onSwitchCameraClicked(View view) {
        // running on UI thread
        if (mUidList.size() > 1) {
            int targetUid = 0;
            for (int i = 0, size = mUidList.size(); i < size; i++) {
                int uid = mUidList.keyAt(i);
                boolean current = mUidList.get(uid);

                if (current) {
                    mUidList.put(uid, false);
                    if (i < size - 1) {
                        targetUid = mUidList.keyAt(i + 1);
                    } else {
                        targetUid = mUidList.keyAt(0);
                    }
                    break;
                }
            }
            mUidList.put(targetUid, true);
            // targetUid should not be 0
            doSetupVideoStreamView(targetUid);
        } else {
            Toast.makeText(this,"只有一个摄像头 不能切换",Toast.LENGTH_SHORT).show();
        }
    }

}
