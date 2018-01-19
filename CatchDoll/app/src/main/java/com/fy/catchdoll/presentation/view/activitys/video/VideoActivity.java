package com.fy.catchdoll.presentation.view.activitys.video;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.widgets.myvideo.VideoConstants;
import com.fy.catchdoll.library.widgets.myvideo.element.CustomIjkVideoView;
import com.fy.catchdoll.library.widgets.myvideo.inter.IXVideoPlayListener;
import com.fy.catchdoll.library.widgets.video.ijkplayer.media.IRenderView;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

/**
 * Created by xky on 2018/1/12 0012.
 */
public class VideoActivity extends AppCompatBaseActivity implements IXVideoPlayListener {
    public static final String URL_KEY = "vido_url_key";

    private CustomIjkVideoView mVideoView;
    private View mStateContainer;
    private View mLoadingStateView;
    private View mErrorStateView;
    private String url;
    @Override
    public int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    public void initView() {
        mVideoView = (CustomIjkVideoView) findViewById(R.id.videoview);
        mVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        mLoadingStateView = findViewById(R.id.play_loading_container);
        mErrorStateView = findViewById(R.id.window_play_error);
        mStateContainer = findViewById(R.id.play_state_container);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_video_title));
        Intent intent = getIntent();
        if (intent != null){
            url = intent.getStringExtra(URL_KEY);
        }
    }

    @Override
    public void setListener() {
        mVideoView.setOnPlayListener(this);
    }

    @Override
    public void loadData() {
        showVideoLoading();
        if (mVideoView != null && !TextUtils.isEmpty(url)) {
            mVideoView.play(url, VideoConstants.TYPE_NET);
        }
    }

    private void showVideoLoading() {
        mVideoView.setVisibility(View.VISIBLE);
        mStateContainer.setVisibility(View.VISIBLE);
        mLoadingStateView.setVisibility(View.VISIBLE);
        mErrorStateView.setVisibility(View.GONE);
    }

    private void closeVideoLoading(){
        mVideoView.setVisibility(View.VISIBLE);
        mStateContainer.setVisibility(View.GONE);
        mErrorStateView.setVisibility(View.GONE);
        mLoadingStateView.setVisibility(View.GONE);
    }

    private void showErrorView(){
        mVideoView.setVisibility(View.VISIBLE);
        mStateContainer.setVisibility(View.VISIBLE);
        mLoadingStateView.setVisibility(View.GONE);
        mErrorStateView.setVisibility(View.VISIBLE);
    }


    /**********************播放器 start***************************/

    @Override
    public void onLoading() {
        showVideoLoading();
    }

    @Override
    public void onPlay() {
        closeVideoLoading();
    }

    @Override
    public void onPlayProgress(int progress, int maxProgress, int bufprogress) {

    }

    @Override
    public void onPlayCompletion() {
//        showVideoLoading();
    }

    @Override
    public void onError(int errorType) {
        showErrorView();
    }

    @Override
    public void onBlock(boolean isBlockFinish) {
        if (isBlockFinish) {
            // 缓冲完毕
            closeVideoLoading();
        } else {
            // 开始缓冲
            showVideoLoading();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    public void onPlayRending() {
        closeVideoLoading();
    }

    /**********************播放器 end***************************/

}
