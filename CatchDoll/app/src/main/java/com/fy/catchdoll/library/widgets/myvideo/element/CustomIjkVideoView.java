package com.fy.catchdoll.library.widgets.myvideo.element;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


import com.fy.catchdoll.library.utils.PushLogUtils;
import com.fy.catchdoll.library.widgets.myvideo.DensityUtil;
import com.fy.catchdoll.library.widgets.myvideo.VideoConstants;
import com.fy.catchdoll.library.widgets.myvideo.inter.IVideoPlayListener;
import com.fy.catchdoll.library.widgets.myvideo.inter.IXVideoPlayListener;
import com.fy.catchdoll.library.widgets.video.ijkplayer.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by xky on 2016/12/23 0023.
 */
public class CustomIjkVideoView extends IjkVideoView implements IMediaPlayer.OnPreparedListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnErrorListener {
    private Context mContext;
    // 屏幕宽高
    private float width;
    private float height;
    private int videoWidth;
    private int videoHeight;
    public static final String TAG = "video";
    private IVideoPlayListener mPlayListener;

    public CustomIjkVideoView(Context context) {
        super(context);
    }

    public CustomIjkVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public CustomIjkVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    public CustomIjkVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////		int width = getDefaultSize(videoWidth, widthMeasureSpec);
////		int height = getDefaultSize(videoHeight, heightMeasureSpec);
//        int width = (int) this.width;
//        int height = (int) this.height;
//        if (videoWidth > 0 && videoHeight > 0) {
//            if (videoWidth * height > width * videoHeight) {
//                height = width * videoHeight / videoWidth;
//            } else if (videoWidth * height < width * videoHeight) {
//                width = height * videoWidth / videoHeight;
//            }
//        }
//        setMeasuredDimension(width, height);
//    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }


    public void setWidthAndHeight(float width1,float height1){
        width = width1;
        height = height1;
    }

    private void initData(Context context) {
        mContext = context;
        width = DensityUtil.getWidthInPx(mContext);
        height = DensityUtil.getHeightInPx(mContext);
        setOnPreparedListener(this);
        setOnCompletionListener(this);
        setOnInfoListener(this);
        setOnErrorListener(this);
//        setAspectRatio(IRenderView.AR_ASPECT_FILL_PARENT);
//        setAspectRatio(IRenderView.AR_ASPECT_WRAP_CONTENT);
    }


    /**
     *
     *<p>播放视频</p><br/>
     *<p></p><br/>
     * @since 1.0.0
     * @author xky
     * @param url 视频路径【后期处理 本地和网络两种路径】
     */
    public void play(String url,int type){
        if(type == VideoConstants.TYPE_LOCAL){
            //本地
            setVideoPath(url);
        }else{
            //网络
            setVideoURI(Uri.parse(url));
        }
        requestFocus();
        onLoading_();
        PushLogUtils.i(TAG, "play=" + url);
    }

    /**
     *
     *<p>播放或者暂停</p><br/>
     * @since 1.0.0
     * @author xky
     */
    public void playOrPause(){
        if(!isPlaying()){
            onPlayStart_();
            start();
        }else{
            onPlayPause();
            pause();
        }
    }

    /**
     *
     *<p>继续播放</p><br/>
     * @since 1.0.0
     * @author xky
     */
    public void goonPlay(){
        if(!isPlaying()){
            onPlayStart_();
            start();
        }
    }

    /**
     *
     *<p>暂停播放</p><br/>
     * @since 1.0.0
     * @author xky
     */
    public void pausePlay(){
        if(isPlaying()){
            onPlayPause();
            pause();
        }
    }

    @Override
    public void stopPlayback() {
        super.stopPlayback();
        mHandler.removeMessages(1);
        release(true);
        IjkMediaPlayer.native_profileEnd();
    }

    /**
     * 准备播放
     * @param mp
     */
    @Override
    public void onPrepared(IMediaPlayer mp) {
        onPlayStart_();
        setVideoWidth(mp.getVideoWidth());
        setVideoHeight(mp.getVideoHeight());
        start();
        onPlayProgress_(getCurrentPosition(), getDuration(), getBufferPercentage());
        mHandler.sendEmptyMessageDelayed(1, 1000);
        PushLogUtils.i(TAG, "onPrepared="+System.currentTimeMillis());
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (getCurrentPosition() > 0) {
                        onPlayProgress_(getCurrentPosition(), getDuration(), getBufferPercentage());
                    } else {
                        onPlayProgress_(0, getDuration(), getBufferPercentage());
                    }
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 播放完成
     * @param mp
     */
    @Override
    public void onCompletion(IMediaPlayer mp) {
        onPlayCompletion_();
        PushLogUtils.i(TAG, "onCompletion=");
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        PushLogUtils.i(TAG, "onInfo=");

        //监听播放卡、监听开始拖动、监听卡完成、监听拖动缓冲完成。
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //监听播放卡、监听开始拖动
//			Toast.makeText(mContext, "开始缓冲", 1).show();
                onBlock(false);
                break;

            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                //监听卡完成、监听拖动缓冲完成
//			Toast.makeText(mContext, "缓冲结束", 1).show();
                onBlock(true);
                break;

            case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                //显示下载速度
             //   PushLogUtils.i("Progress", "MEDIA_INFO_NETWORK_BANDWIDTH_extra" + extra);
                break;
            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                //PushLogUtils.i(TAG, "MEDIA_INFO_VIDEO_RENDERING_START_extra" + System.currentTimeMillis());
                onBlock(true);
                onRending();
                break;
            default:
                break;
        }
        return false;
    }


    /**
     * 播放出错
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        PushLogUtils.i(TAG, "onError=");
        onError_(what);
        return false;
    }

    public void setOnPlayListener(IVideoPlayListener playListener){
        mPlayListener = playListener;
    }

    public void onLoading_(){
        if(mPlayListener != null){
            mPlayListener.onLoading();
        }
    }
    public void onPlayStart_(){
        if(mPlayListener != null){
            mPlayListener.onPlay();
        }
    }
    public void onPlayProgress_(long l,long maxProgress,int bufProgress){
        if(mPlayListener != null){
            int current  = (int) l;
            int max = (int) maxProgress;
            mPlayListener.onPlayProgress(current, max < 0 ? 0 : max, bufProgress);
        }
    }
    public void onPlayCompletion_(){
        if(mPlayListener != null){
            mPlayListener.onPlayCompletion();
        }
    }
    public void onPlayPause(){
        if(mPlayListener != null){
            mPlayListener.onPause();
        }
    }

    public void onError_(int type){
        if(mPlayListener != null){
            mPlayListener.onError(type);
        }
    }

    public void onBlock(boolean isBlockFinish){
        if(mPlayListener != null){
            mPlayListener.onBlock(isBlockFinish);
        }
    }

    public void onRending() {
        if (mPlayListener != null && mPlayListener instanceof IXVideoPlayListener) {
            ((IXVideoPlayListener) mPlayListener).onPlayRending();
        }
    }
}
