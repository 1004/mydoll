package com.fy.catchdoll.presentation.view.activitys.room;

import com.fy.catchdoll.R;
import com.fy.catchdoll.presentation.presenter.room.VoicePresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;
import com.fy.catchdoll.presentation.view.fragements.room.LiveViewFragement;
import com.fy.catchdoll.presentation.view.fragements.room.MainDialogFragment;

/**
 * Created by xky on 2017/11/20 0020.
 *
 * 直播间
 */
public class RoomActivity extends AppCompatBaseActivity{
    private VoicePresenter mVoicePresenter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_room;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        LiveViewFragement liveViewFragment = new LiveViewFragement();
        getSupportFragmentManager().beginTransaction().add(R.id.room_main, liveViewFragment).commit();
        mVoicePresenter = new VoicePresenter(this);
        new MainDialogFragment().show(getSupportFragmentManager(),"MainDialogFragment");
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

    @Override
    public void setListener() {

    }

    @Override
    public void loadData() {

    }
}
