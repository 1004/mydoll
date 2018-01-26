package com.fy.catchdoll.library.widgets.myvideo;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.Device;
import com.fy.catchdoll.library.widgets.myvideo.element.CustomIjkVideoView;
import com.fy.catchdoll.library.widgets.myvideo.inter.IXVideoPlayListener;

import java.util.ArrayList;
import java.util.List;

import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by xky on 2018/1/25 0025.
 */
public class VideoViewLayout extends RelativeLayout implements View.OnClickListener {
    private List<String> mPaths;
    private Context mContext;
    private LayoutInflater mInflater;
    // 屏幕宽高
    private float mWidth;
    private float mHeight;

    private RelativeLayout mNetErrorContainer;
    private CustomIjkVideoView mVideo;
    private DanmakuView mBarrageView2;
    private LinearLayout mSelfLoading;
    private boolean isError = false;
    private int mCurrentPath = 0;
    private boolean isSwitchSuccess;

    public VideoViewLayout(Context context) {
        super(context);
    }

    public VideoViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        setListener();
    }

    public VideoViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initData(Context context) {
        mPaths = new ArrayList<String>();
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mWidth = DensityUtil.getWidthInPx(mContext);
        mHeight = DensityUtil.getHeightInPx(mContext);
    }
    private void initView() {
        View mView = mInflater.inflate(R.layout.videoview, this);

        mNetErrorContainer = (RelativeLayout) findViewById(R.id.net_error_container);
        mNetErrorContainer.setVisibility(View.GONE);
        mVideo = (CustomIjkVideoView) findViewById(R.id.videoview);
        mBarrageView2 = (DanmakuView) findViewById(R.id.play_dm);
        mSelfLoading = (LinearLayout) findViewById(R.id.selfloading);

    }

    private void setListener() {
        mVideo.setOnPlayListener(new IXVideoPlayListener() {

            @Override
            public void onPlayRending() {
                onPlayRending_();
            }

            @Override
            public void onPlayProgress(int progress, int maxProgress, int bufprogress) {
                onPlayProgress_(progress, maxProgress, bufprogress);
            }

            @Override
            public void onPlayCompletion() {
                onPlayCompletion_();
            }

            @Override
            public void onPlay() {
                onPlay_();
            }

            @Override
            public void onPause() {
                onPause_();
            }

            @Override
            public void onLoading() {
                onLoading_();
            }

            @Override
            public void onError(int type) {
                onError_(type);
            }

            @Override
            public void onBlock(boolean isBlockFinish) {
                onBlock_(isBlockFinish);
            }
        });

        // mScaleImg.setOnClickListener(this);
        mNetErrorContainer.setOnClickListener(this);
    }

    /**
     * 渲染数据
     */
    private void onPlayRending_() {
        Log.i("test", "onPlayRending_");

//        if (mCompletionListener != null) {
//            mCompletionListener.onPlayRending();
//        }
//        if (mRoomDto != null) {
//            mVideoSyIv.setVisibility(View.VISIBLE);
//            ImageLoader.getInstance().displayImage(mRoomDto.getWater_mark(), mVideoSyIv);
//        }
    }

    public void showLoading() {
        mSelfLoading.setVisibility(View.VISIBLE);
        Log.i("test", "开始缓2");
    }

    public void closeLoading() {
        mSelfLoading.setVisibility(View.GONE);
         Log.i("test", "结束缓2");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.net_error_container:
                // 刷新网络
                play(mPaths.get(mCurrentPath));
                mNetErrorContainer.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 设置播放媒体集合 【默认从第一个播放】
     */
    public void setVideoPaths(List<String> paths) {
        if (paths != null) {
            mPaths.clear();
            mPaths.addAll(paths);
        }
        playPath();
    }

    /**
     * 播放
     */
    private void playPath(){
        isSwitchSuccess = false;
        if (mPaths.size() > 0 && mCurrentPath<mPaths.size()) {
            play(mPaths.get(mCurrentPath));
            mNetErrorContainer.setVisibility(View.GONE);
        }
    }

    /**
     * 切换
     * @param index
     */
    public void switchPath(int index){
        if (mCurrentPath == index){
            return;
        }
        if (mVideo != null) {
            mVideo.stopPlayback();
        }
        mCurrentPath = index;
        playPath();
    }

    public List<String> getPaths(){
        return mPaths;
    }

    /**
     * Activity销毁时调用
     */
    public void setVideoDestory() {
        if (mVideo != null) {
            mVideo.stopPlayback();
        }
        mVideo = null;
    }

    private void play(String url) {
        if (mVideo != null) {
            mVideo.play(url, VideoConstants.TYPE_LOCAL);
        }
    }
    /**
     * 设置进度的
     */
    private void onPlayProgress_(int progress, int maxProgress, int bufprogress) {
        //operateVideoInfo();
//        Log.i("test", "onPlayProgress_");
    }

    private void onPlayCompletion_() {
        Log.i("test", "onPlayCompletion_");
        showLoading();
    }

    /**
     * 播放回掉
     */
    private void onPlay_() {
        Log.i("test", "onPlay_");
        isSwitchSuccess = true;
        closeLoading();
    }

    /**
     * 暂停回掉
     */
    private void onPause_() {
        Log.i("test", "onPause_");
        closeLoading();
    }
    /**
     * 加载中的回掉
     */
    private void onLoading_() {
        Log.i("test", "onLoading_");
        showLoading();
    }
    /**
     * 错误回掉
     */
    private void onError_(int type) {
        Log.i("test","onError_");
        // Toast.makeText(mContext, "播放失败", 0).show();
        setErrorTyp(type);
        // 播放出错
        isError = true;
        // 或者是网络异常，或者是不支持该格式，本地视频不存在网络异常
        // Toast.makeText(mActivity, "播放出错啦....", 0).show();
        showNoNetInfo();
    }

    public boolean getSwitchState(){
        return isSwitchSuccess;
    }

    private void setErrorTyp(int errorType) {
        closeLoading();
        String errorMsg = "";
        switch (errorType) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                // aler.setMessage("抱歉，播放器出错了！");
                errorMsg = "抱歉，播放器出错了";
                break;

            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                // aler.setMessage("抱歉，该视频无法拖动！");
                errorMsg = "抱歉，该视频无法拖动！";

                break;

            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                // aler.setMessage("抱歉，暂时无法播放该视频！");
                errorMsg = "抱歉，该视频无法拖动！";

                break;
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                // aler.setMessage("抱歉，该视频文件格式错误！");
                errorMsg = "抱歉，该视频文件格式错误！";

                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                // aler.setMessage("抱歉，解码时出现");
                errorMsg = "抱歉，解码时出现";

                break;

            default:
                // aler.setMessage("抱歉，该视频无法播放！");
                errorMsg = "抱歉，该视频无法播放！";

                break;
        }
        Log.i("test", "出错了" + errorMsg);
    }

    /**
     * 无网络提示信息
     */
    private boolean showNoNetInfo() {
        if (!Device.isOnline(mContext)) {
            // 如果当前是网络视频 并且没有网络,或者网络不好 提示信息
            operateNoNetError();
            return true;
        } else {
            operateNoNetError();
            return false;
        }
    }

    /**
     * //TODO
     * 处理无网络的情况，或者超时的请求
     */
    private void operateNoNetError() {
        if (Device.isOnline(mContext)) {
            Toast.makeText(mContext, "数据连接失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
        }
        mNetErrorContainer.setVisibility(View.VISIBLE);
    }

    private void onBlock_(boolean isBlockFinish) {
        Log.i("test", "onBlock_");
        // 播放卡
        if (isBlockFinish) {
            // 缓冲完毕
            // mManager.videoCloseLoading();
            closeLoading();
            Log.i("test", "结束缓");
        } else {
            // 开始缓冲
            // mManager.videoOpenloading();
            showLoading();
            Log.i("test", "开始缓");
        }
    }



}
