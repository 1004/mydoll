package com.fy.catchdoll.presentation.view.activitys.room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.library.utils.tool.SoftKeyBoardListener;
import com.fy.catchdoll.library.widgets.CustomRecentView;
import com.fy.catchdoll.library.widgets.LongPressImageView;
import com.fy.catchdoll.library.widgets.NetStateView;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.library.widgets.myvideo.VideoConstants;
import com.fy.catchdoll.library.widgets.myvideo.VideoViewLayout;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.module.support.agora.common.Constant;
import com.fy.catchdoll.module.support.agora.model.AGEventHandler;
import com.fy.catchdoll.module.support.agora.model.ConstantApp;
import com.fy.catchdoll.module.support.recharge.RechargeNotifyManager;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.msg.MessDto;
import com.fy.catchdoll.presentation.model.dto.room.CatchRecord;
import com.fy.catchdoll.presentation.model.dto.room.EnterRoomDto;
import com.fy.catchdoll.presentation.model.dto.room.GetDollDto;
import com.fy.catchdoll.presentation.model.dto.room.OperateMachineDto;
import com.fy.catchdoll.presentation.model.dto.room.RoomInfo;
import com.fy.catchdoll.presentation.model.dto.room.VideoUrl;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.msg.DmPresenter;
import com.fy.catchdoll.presentation.presenter.msg.MessageNotifyManager;
import com.fy.catchdoll.presentation.presenter.room.RoomPresenter;
import com.fy.catchdoll.presentation.presenter.room.VoicePresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.adapters.room.MessageAdapter;
import com.fy.catchdoll.presentation.view.adapters.wrap.WrapConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Created by xky on 2017/12/1 0001.
 */
public class DollRoomActivity extends AppCompatBaseActivity implements AGEventHandler, View.OnLongClickListener, CustomRecentView.OnHistoryClickListener, RechargeNotifyManager.OnPayStateListener, MessageNotifyManager.IOnMessageComeCallBack {
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
    private TextView mTotalGoldTv;
    private int mCurrentState;

    private TextView mStateStartTv;
    private TextView mPayGoldTv;
    private View mPayContainer;

    private LongPressImageView mLeftIcon;
    private LongPressImageView mRightIcon;
    private LongPressImageView mTopIcon;
    private LongPressImageView mDownIcon;
    private View mCatchView;
    private boolean isCatch;

    private View mTimeHintContainer;
    private TextView mTimeHintCount;
    private TextView mOperationHint;
    private Handler mHandler = new Handler();
    private OperateMachineDto mOperateionDto;
    private boolean isPayCome = false;
    private int mPlayTimeCount = 30;
    public static final String DOLL_ROOM_PAY = "DollRoomActivity_pay";
    private ListView mMsgListView;
    private MessageAdapter mMsgAdapter;
    private boolean isDestory;
    private VideoViewLayout mVideo;

    private FrameLayout container;
    private List<String> mPaths = new ArrayList<>();
    private String[] flvs;
    private int mCurrentDrive;
    private int mCurrentCameraId;
    public static final int WAWA_AGORA = 2;
    public static final int WAWA_JIN = 1;
    private int mDialogCount = 10;
    private boolean isCatchSuccess ;
    private boolean isCatchResultReturn = false;//抓取结果是否返回

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

        mStateStartTv = (TextView) findViewById(R.id.room_state_start_tv);
        mPayContainer = findViewById(R.id.room_pay_container);

        mLeftIcon = (LongPressImageView) findViewById(R.id.oriention_left_icon);
        mRightIcon = (LongPressImageView) findViewById(R.id.oriention_right_icon);
        mTopIcon = (LongPressImageView) findViewById(R.id.oriention_top_icon);
        mDownIcon = (LongPressImageView) findViewById(R.id.oriention_bottom_icon);
        mCatchView = findViewById(R.id.room_catch_doll);

        mTimeHintContainer = findViewById(R.id.room_time_hint_container);
        mTimeHintCount = (TextView) findViewById(R.id.room_time_hint_count);
        mOperationHint = (TextView) findViewById(R.id.room_operation_hint);
        mMsgListView = (ListView) findViewById(R.id.msg_list);
        mVideo = (VideoViewLayout) findViewById(R.id.video);
        container = (FrameLayout) findViewById(R.id.gaming_video);

    }

    @Override
    public void initData() {
        inittopViewH();
        initIntent();
        initDanMu();
        initAdapter();
        handler = new Handler();
        dialogManager = new DialogManager(this);
        mVoicePresenter = new VoicePresenter(this);
        mRoomPresenter = new RoomPresenter();
    }

    private void initAdapter() {
        mMsgAdapter = new MessageAdapter(this,mMsgListView);
        mMsgListView.setAdapter(mMsgAdapter);
    }

    private void initDanMu() {
        DmPresenter.getInstance().clearCacheMegs();
        DmPresenter.getInstance().init();

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
//        int cRole = Constants.CLIENT_ROLE_BROADCASTER;
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
        mRoomPresenter.registOperateMachineCallBack(mOperateMachineCallBack);
        mRoomPresenter.registGetDollInfoCallBack(mGetDollInfoCallBack);
        mPayContainer.setOnClickListener(this);

        mLeftIcon.setOnTimeClickListener(this);
        mRightIcon.setOnTimeClickListener(this);
        mTopIcon.setOnTimeClickListener(this);
        mDownIcon.setOnTimeClickListener(this);
        mCatchView.setOnClickListener(this);
        mRecentDoll.setOnHistoryClickListener(this);
        findViewById(R.id.room_record_container).setOnClickListener(this);
        RechargeNotifyManager.getInstance().registPayStateListener(DOLL_ROOM_PAY, this);
        MessageNotifyManager.getInstance().registGiftCallBack(DOLL_ROOM_KEY,this);
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

    private IBasePresenterLinstener mOperateMachineCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (obj != null && obj instanceof OperateMachineDto){
                OperateMachineDto roomDto = (OperateMachineDto) obj;
                startPlaySuccess(roomDto);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            ErrorCodeOperate.executeError(TAG, getContext(), code, msg, true);
            isCatch = false;
            if (code == 4003 || code == 4004 || code == 4005 || code == 4006){
                changeStartState(false,RoomInfo.STATE_GAME_FREE);
            }
        }
    };

    private IBasePresenterLinstener mGetDollInfoCallBack = new IBasePresenterLinstener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (obj != null && obj instanceof GetDollDto){
                if (isCatchResultReturn){
                    return;
                }else {
                    isCatchResultReturn = true;
                }
                Log.i("test","-mGetDollInfoCallBack--");
                GetDollDto dollDto = (GetDollDto) obj;
                operateGetDoll(dollDto);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            ErrorCodeOperate.executeError(TAG, getContext(), code, msg, true);
            isCatch = false;
            mOperationHint.setVisibility(View.GONE);
            changeStartState(false,RoomInfo.STATE_GAME_FREE);
        }
    };

    private void operateGetDoll(GetDollDto dollDto) {
        mOperationHint.setVisibility(View.GONE);
        mDialogCount = 10;
        if (dollDto.getIs_grab() == GetDollDto.GRAB_OK){
            isCatchSuccess = true;
            //抓中
            dialogManager.showDialog(DialogStyle.CATCH_SUCCESS,mCatchSuccessListener
                    ,dollDto.getDoll_info()
//                    ,getMString(R.string.string_room_look_box)
                    ,getMString(R.string.string_room_get_failed_left)
                    ,getResources().getString(R.string.string_room_success_getonce,mDialogCount));
            mHandler.postDelayed(mDialogTask,1000);
        }else {
            isCatchSuccess = false;
            //未抓中
            dialogManager.showDialog(DialogStyle.CATCH_FAILED,mCatchFailedListener
                    ,getMString(R.string.string_room_get_failed_hint)
                    ,getMString(R.string.string_room_get_failed_left)
                    ,getResources().getString(R.string.string_room_get_failed_right_count, mDialogCount));
            mHandler.postDelayed(mDialogTask,1000);
        }
    }

    private Runnable mDialogTask = new Runnable() {
        @Override
        public void run() {
            mDialogCount --;
            Log.i("test","count:"+mDialogCount);
            mHandler.removeCallbacks(mDialogTask);
            if (isCatchSuccess){
                dialogManager.setTimeText(getResources().getString(R.string.string_room_success_getonce,mDialogCount));
            }else {
                dialogManager.setTimeText(getResources().getString(R.string.string_room_get_failed_right_count,mDialogCount));
            }
            if (mDialogCount>0){
                mHandler.postDelayed(mDialogTask,1000);
            }else {
                //时间到
                dialogManager.dismissDialog();
                mRoomPresenter.operateMachine(roomId, OperateMachineDto.CANCEL, getCurrentCameraId());
                //取消，切换到空闲
                changeStartState(false, RoomInfo.STATE_GAME_FREE);
            }
        }
    };

    private DialogManager.OnClickListenerContent mCatchSuccessListener = new DialogManager.OnClickListenerContent() {
        @Override
        public void onClick(View view, Object... content) {
            switch (view.getId()){
                case R.id.tv_yes_common:
                    dialogManager.dismissDialog();
                    mHandler.removeCallbacks(mDialogTask);
                    //乘胜追击 自动上机
                    changeStartState(false,RoomInfo.STATE_GAME_FREE);
                    operatePlay();
                    break;
                case R.id.tv_cancel_common:
                    dialogManager.dismissDialog();
                    mHandler.removeCallbacks(mDialogTask);
                    mRoomPresenter.operateMachine(roomId, OperateMachineDto.CANCEL, getCurrentCameraId());
                    //去背包查看 更换状态到空闲
                    changeStartState(false, RoomInfo.STATE_GAME_FREE);
//                    ActivityUtils.startBoxInfoActivity(DollRoomActivity.this);
                    break;
            }
        }
    };

    private DialogManager.OnClickListenerContent mCatchFailedListener = new DialogManager.OnClickListenerContent() {
        @Override
        public void onClick(View view, Object... content) {
            switch (view.getId()){
                case R.id.tv_yes_common:
                    dialogManager.dismissDialog();
                    mHandler.removeCallbacks(mDialogTask);
                    //再来一局--自动上机
                    changeStartState(false,RoomInfo.STATE_GAME_FREE);
                    operatePlay();
                    break;
                case R.id.tv_cancel_common:
                    dialogManager.dismissDialog();
                    mHandler.removeCallbacks(mDialogTask);
                    mRoomPresenter.operateMachine(roomId, OperateMachineDto.CANCEL, getCurrentCameraId());
                    //取消，切换到空闲
                    changeStartState(false, RoomInfo.STATE_GAME_FREE);
                    break;
            }
        }
    };

    private void parseRoomData(EnterRoomDto roomDto) {
        mNetView.show(NetStateView.NetState.CONTENT);
        initVideo(roomDto);
        initNetData(roomDto);
    }

    private void initVideo(EnterRoomDto roomDto){
        VideoUrl videoUrl = roomDto.getLive_url();
        if (videoUrl == null){
            return;
        }
        mCurrentDrive = videoUrl.getDrive();
        if (mCurrentDrive == WAWA_JIN) {
            container.setVisibility(View.GONE);
            mVideo.setVisibility(View.VISIBLE);
            mPaths.clear();
            flvs = videoUrl.getFlv().split(",");
            if (flvs == null || flvs.length == 0){
                return;
            }
            mCurrentCameraId = Constant.Wawaji_CAM_MAIN;
            mPaths = Arrays.asList(flvs);
            mVideo.setVideoPaths(mPaths);
        }else {
            container.setVisibility(View.VISIBLE);
            mVideo.setVisibility(View.GONE);
            //初始化第三方的播放器
            initAgora(videoUrl.getChannel_id());
        }

    }

    private void initNetData(EnterRoomDto roomDto) {
        mRoomDto = roomDto;
        RoomInfo machine = roomDto.getMachine();
        User user = roomDto.getUser();
        List<CatchRecord> grab_record = roomDto.getGrab_record();

        if (roomDto != null){
            //连接弹幕
            DmPresenter.getInstance().linkDanmu(roomDto.getDanmu(),machine.getId());
        }

        if (machine != null && user != null){
            User accoundUser = AccountManager.getInstance().getUser();
            if (accoundUser != null){
                try {
                    accoundUser.setGold(user.getGold());
                }catch (Throwable e){

                }
            }
            changeStartState(false,machine.getGame_state());
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
            case R.id.room_pay_container:
                ActivityUtils.startRechargeListActivity(this);
                break;
            case R.id.oriention_left_icon:
                operatePlayState(OperateMachineDto.LEFT);
                break;
            case R.id.oriention_right_icon:
                operatePlayState(OperateMachineDto.RIGHT);
                break;
            case R.id.oriention_top_icon:
                operatePlayState(OperateMachineDto.UP);
                break;
            case R.id.oriention_bottom_icon:
                operatePlayState(OperateMachineDto.DOWN);
                break;
            case R.id.room_catch_doll:
                operatePlayState(OperateMachineDto.GET);
                break;
            case R.id.room_record_container:
                ActivityUtils.startCatchHistoryActivity(this);
                break;
        }
    }

    private void operatePlayState(String type){
        if (isCatch){
            //正在去抓 则不会继续后续操作
            return;
        }
        if (OperateMachineDto.GET.equals(type)){
            //去抓了
            isCatch = true;
            endPlayTask();
        }
        VoicePresenter.vSimple(this);

        mRoomPresenter.operateMachine(roomId, type, getCurrentCameraId());
    }

    private int getCurrentCameraId(){
        if (mCurrentDrive == WAWA_AGORA){
            return config().mWawajiUid;
        }else{
            return mCurrentCameraId;
        }
    }

    /**
     * 启动状态---->操作状态
     */
    private void operatePlay() {
        if (mCurrentState == RoomInfo.STATE_GAMEING){
            //游戏中
            Toast.makeText(this,getResources().getString(R.string.string_room_tost_busy),Toast.LENGTH_SHORT).show();;
        }else {
            //空闲中
            boolean isMoneyEnough = checkMoney();
            if (!isMoneyEnough){
                ActivityUtils.startRechargeListActivity(this);
                return;
            }
            mStateStartTv.setText(getMString(R.string.string_room_start_machine));
            mStartCatchContainer.setOnClickListener(null);
            mRoomPresenter.operateMachine(roomId, OperateMachineDto.START,getCurrentCameraId());
        }
    }

    private boolean checkMoney() {
        User user = AccountManager.getInstance().getUser();
        try {
            if (user != null && mRoomDto != null){
                int gold = Integer.valueOf(mRoomDto.getMachine().getGold());
                return gold<=user.getGold();
            }
        }catch (Throwable e){

        }
        return false;
    }

    /**
     * 操作状态----》上机成功
     */
    private void startPlaySuccess(OperateMachineDto dto){
        if (dto != null){
            User user = AccountManager.getInstance().getUser();
            if (user != null && OperateMachineDto.START.equals(dto.getType())){
                isCatch = false;
                isCatchResultReturn = false;
                user.setGold(dto.getGold());
                changeStartState(true, RoomInfo.STATE_GAMEING);
            }
            mOperateionDto = dto;
            if (OperateMachineDto.GET.equals(dto.getType())){
                //抓取后，开始获取当前抓取的结果 在请求抓取结果后 将isCatch置为false
                mOperationHint.setVisibility(View.VISIBLE);
                mOperationHint.setText(getMString(R.string.string_room_get_info));
                mHandler.postDelayed(mCatchWaitTask, 15 * 1000);
            }
        }
    }

    private Runnable mCatchWaitTask = new Runnable() {
        @Override
        public void run() {
            if (mOperateionDto != null){
                mRoomPresenter.getGrepDollInfo(mOperateionDto.getRecord_id(),roomId);
            }
        }
    };

    /**
     * 操作状态----》上机失败
     * 切换到开始上机的位置----》根据state来选择是等待，还是可以上机
     * 操作状态
     *      未上机
     *          等待
     *          空闲
     *      上机
     */
    private void changeStartState(boolean isChangeOperation,int state){
        if (isChangeOperation){
            //变成操作状态了
            initOperateState();
        }else {
            //是不可以操作的状态
            initMachineFirstState(state);
        }
    }

    /**
     * 可操作状态
     */
    private void initOperateState(){
        mRoomStateStartWaitContainer.setVisibility(View.GONE);
        mRoomStatePlayContainer.setVisibility(View.VISIBLE);
        mTimeHintContainer.setVisibility(View.VISIBLE);
        startPlayTask();
    }


    /**
     * 开启定时任务
     */
    private void startPlayTask(){
        mPlayTimeCount = 30;
        mTimeHintCount.setText(mPlayTimeCount+"");
        mHandler.postDelayed(mPlayTask, 1000);
    }

    /**
     * 结束定时任务
     */
    private void endPlayTask(){
        mHandler.removeCallbacks(mPlayTask);
    }

    private Runnable mPlayTask = new Runnable() {
        @Override
        public void run() {
            mPlayTimeCount--;
            mTimeHintCount.setText(mPlayTimeCount+"");
            endPlayTask();
            if (mPlayTimeCount <= 0){
                //结束计时
                operatePlayState(OperateMachineDto.GET);
            }else {
                //时间未到
                mHandler.postDelayed(mPlayTask, 1000);
            }
        }
    };


    /**
     * 不可操作状态的提示
     * 在未开启操作的时候的状态
     * @param game_state
     */
    private void initMachineFirstState(int game_state) {
        mCurrentState = game_state;
        mTimeHintContainer.setVisibility(View.GONE);
        endPlayTask();
        mStartCatchContainer.setOnClickListener(this);
        mStateStartTv.setText(getMString(R.string.string_room_state_start));
        mRoomStateStartWaitContainer.setVisibility(View.VISIBLE);
        mRoomStatePlayContainer.setVisibility(View.GONE);

        User user = AccountManager.getInstance().getUser();
        if (user != null){
            mTotalGoldTv.setText(user.getGold()+"");
        }

        if (mRoomDto != null && mRoomDto.getMachine()!= null){
            mPayGoldTv.setText(getResources().getString(R.string.string_room_pay_gold, mRoomDto.getMachine().getGold()));
        }

        if (mCurrentState == RoomInfo.STATE_GAMEING){
            //游戏中
            mStartCatchContainer.setBackgroundResource(R.drawable.shape_round_state_wait);
            mStateStartTv.setTextColor(getResources().getColor(R.color.color_3B4152));
            mPayGoldTv.setTextColor(getResources().getColor(R.color.color_3B4152));
        }else {
            //空闲中
            mStartCatchContainer.setBackgroundResource(R.drawable.shape_round_state_start);
            mStateStartTv.setTextColor(getResources().getColor(R.color.color_white));
            mPayGoldTv.setTextColor(getResources().getColor(R.color.color_white));
        }
    }


    /**
     * 等待状态--->启动状态
     */
    private void operateStart(){
        mRoomStateStartWaitContainer.setVisibility(View.VISIBLE);
        mRoomStatePlayContainer.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mVoicePresenter.startPlayBgVoice();
        if (isPayCome){
            updateGold();
        }
        isPayCome = false;
    }

    private void updateGold(){
        User user = AccountManager.getInstance().getUser();
        if (user != null){
            mTotalGoldTv.setText(user.getGold() + "");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mVoicePresenter.pausePlayBgVoice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        desotry();
    }

    @Override
    public void finish() {
        super.finish();
        desotry();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void desotry(){
        if (isDestory){
            return;
        }
        isDestory = true;
        Log.i("test","--------销毁资源");
        DmPresenter.getInstance().desotryData();
        destoryAgora();
        mVoicePresenter.endPlayBgVoice();
        mHandler.removeCallbacks(mCatchWaitTask);
        mHandler.removeCallbacks(mDialogTask);
        endPlayTask();
        RechargeNotifyManager.getInstance().unRegistPayStateListener(DOLL_ROOM_PAY);
        MessageNotifyManager.getInstance().unRegisteGiftCallBack(DOLL_ROOM_KEY);
        if (mVideo != null) {
            mVideo.setVideoDestory();
        }
    }

    private void destoryAgora(){
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

        if (container.getChildCount() >= 2) {
            return;
        }
        container.removeAllViews();
        container.addView(surfaceV);

        config().mWawajiUid = uid;
    }

    public void onSwitchCameraClicked(View view) {
        // running on UI thread
        if (mCurrentDrive == WAWA_JIN){
            checkIjkCamera();
        }else if (mCurrentDrive == WAWA_AGORA){
            checkAgoraCamera();
        }
    }

    private void checkIjkCamera(){
        if (!mVideo.getSwitchState()){
            Toast.makeText(this,"摄像头切换中",Toast.LENGTH_SHORT).show();
            return;
        }
        if (flvs != null && flvs.length>1){
            mCurrentCameraId = (mCurrentCameraId == Constant.Wawaji_CAM_MAIN ? Constant.Wawaji_CAM_SECONDARY : Constant.Wawaji_CAM_MAIN);
            mVideo.switchPath(mCurrentCameraId - 1);
        }else {
            Toast.makeText(this,"只有一个摄像头 不能切换",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAgoraCamera(){
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

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onHistoryClick(CatchRecord record) {
        ActivityUtils.startPlayerActivity(this,record.getVideo_link());
    }

    @Override
    public void onPayState(int state) {
        isPayCome = true;
    }

    @Override
    public void onMessageCome(MessDto msg) {
        Log.i("test", msg.toString());
        switch (msg.getWaka_type()){
            case MessDto.WAWA_MSG:
                operateAddMsg(msg);
                break;
            case MessDto.WAWA_MACHINE_STATE_BUSY:
                operateBusyState(msg);
//                operateAddMsg(msg);
                break;
            case MessDto.WAWA_MACHINE_STATE_FREE:
                operateFreeState(msg);
//                operateAddMsg(msg);
                break;
            case MessDto.WAWA_MACHINE_STATE_FINISH:
                operateFinishState(msg);
//                operateAddMsg(msg);
                break;
            case MessDto.WAWA_ENTER_ROOM:
                operateAddMsg(msg);
                break;
        }

    }

    /**
     * 获取结果
     * @param msg
     */
    private void operateFinishState(MessDto msg) {
        User user = AccountManager.getInstance().getUser();
        Log.i("test","operateFinishState");
        if (msg != null && user != null && user.getId().equals(msg.getUser_id())){
            if (isCatchResultReturn){
                return;
            }else {
                isCatchResultReturn = true;
                mHandler.removeCallbacks(mCatchWaitTask);
                operateGetDoll(msg.getGrab_data());
            }
        }
    }

    /**
     * 下机状态
     * @param msg
     */
    private void operateFreeState(MessDto msg) {
        changeStartState(false, RoomInfo.STATE_GAME_FREE);
    }

    /**
     * 通知上机状态
     */
    private void operateBusyState(MessDto msg) {
        User user = AccountManager.getInstance().getUser();

        if (msg != null && user != null && user.getId().equals(msg.getUser_id())){
            //当前用户上机了，不用任何操作
        }else {
            //其他用户 等待上机
            changeStartState(false,RoomInfo.STATE_GAMEING);
        }
    }

    private void operateAddMsg(MessDto msg) {
        if (mMsgAdapter != null){
            mMsgAdapter.addData(msg);
        }
    }

    @Override
    public void onMessageChanges(List<MessDto> msgs) {

    }
}
