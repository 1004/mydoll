package com.qike.telecast.presentation.view.activitys.room;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.DeviceUtils;
import com.qike.telecast.library.utils.tool.SoftKeyBoardListener;
import com.qike.telecast.library.widgets.CustomRecentView;
import com.qike.telecast.library.widgets.dialog.DialogManager;
import com.qike.telecast.library.widgets.dialog.DialogStyle;
import com.qike.telecast.presentation.presenter.room.VoicePresenter;
import com.qike.telecast.presentation.view.activitys.base.AppCompatBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2017/12/1 0001.
 */
public class DollRoomActivity extends AppCompatBaseActivity {
    private LinearLayout mVideoChatContainer;
    private View mChatBtn;
    private Handler handler;
    private DialogManager dialogManager;
    private CustomRecentView mRecentDoll;
    private CustomRecentView mDollShow;
    private VoicePresenter mVoicePresenter;
    private View mRoomStateStartWaitContainer;
    private View mRoomStatePlayContainer;
    private View mStartCatchContainer;



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
    }

    @Override
    public void initData() {
        inittopViewH();
        handler = new Handler();
        dialogManager = new DialogManager(this);
        mVoicePresenter = new VoicePresenter(this);
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
    }

    @Override
    public void loadData() {
        testData();
    }

    private void testData(){
        List<Object> objects = new ArrayList<>();
        List<Object> objects2 = new ArrayList<>();
        for (int i=0;i<5;i++){
            objects.add("ddd");
            objects2.add("ddd");
        }
        mRecentDoll.initRecentCatchHistory(objects);
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

}
