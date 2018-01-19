package com.fy.catchdoll.library.widgets.myvideo.element;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.fy.catchdoll.library.widgets.myvideo.DensityUtil;
import com.fy.catchdoll.library.widgets.myvideo.VideoConstants;
import com.fy.catchdoll.library.widgets.myvideo.inter.IVideoOperateListener;
import com.fy.catchdoll.library.widgets.myvideo.inter.IVideoPlayListener;

import java.util.Timer;


/**
 * 自动全屏的VideoView
 */
public class CustomVideoView extends VideoView implements OnPreparedListener, OnCompletionListener, OnErrorListener, OnInfoListener {

	private int videoWidth;
	private int videoHeight;
	private Context mContext;
	
	private float mLastMotionX;
	private float mLastMotionY;
	private int startX;
	private int startY;
	private int threshold;
	private String TAG = "CustomVideoVioView_COM";
	private boolean isClick = true;
	// 屏幕宽高
	private float width;
	private float height;
	
	public CustomVideoView(Context context) {
		super(context);
	}

	public CustomVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initData();
	}


	public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@SuppressLint("NewApi")
	private void initData() {
		threshold = DensityUtil.dip2px(mContext, 18);
		width = DensityUtil.getWidthInPx(mContext);
		height = DensityUtil.getHeightInPx(mContext);
//		setOnTouchListener(mTouchListener);
		setOnPreparedListener(this);
		setOnCompletionListener(this);
		
//		播放卡的监听
		setOnErrorListener(this);
		setOnInfoListener(this);
		
	}
	private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final float x = event.getX();
			final float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = x;
				mLastMotionY = y;
				startX = (int) x;
				startY = (int) y;
				break;
			case MotionEvent.ACTION_MOVE:
				float deltaX = x - mLastMotionX;
				float deltaY = y - mLastMotionY;
				float absDeltaX = Math.abs(deltaX);
				float absDeltaY = Math.abs(deltaY);
				// 声音调节标识
				boolean isAdjustAudio = false;
				if (absDeltaX > threshold && absDeltaY > threshold) {
					if (absDeltaX < absDeltaY) {
						isAdjustAudio = true;
					} else {
						isAdjustAudio = false;
					}
				} else if (absDeltaX < threshold && absDeltaY > threshold) {
					isAdjustAudio = true;
				} else if (absDeltaX > threshold && absDeltaY < threshold) {
					isAdjustAudio = false;
				} else {
					return true;
				}
				if (isAdjustAudio) {
					if (x < width / 2) {
						if (deltaY > 0) {
							lightDown(absDeltaY);
							
						} else if (deltaY < 0) {
							lightUp(absDeltaY);
						}
					} else {
						if (deltaY > 0) {
							volumeDown(absDeltaY);
						} else if (deltaY < 0) {
							volumeUp(absDeltaY);
						}
					}

				} else {
					if (deltaX > 0) {
						Log.e("kk", "absDeltaY2 =" + absDeltaX);
						forward(absDeltaX);
					} else if (deltaX < 0) {
						Log.e("kk", "absDeltaY2 =" + absDeltaX);
						backward(absDeltaX);
					}
				}
				mLastMotionX = x;
				mLastMotionY = y;
				break;
			case MotionEvent.ACTION_UP:
				if (Math.abs(x - startX) > threshold
						|| Math.abs(y - startY) > threshold) {
					isClick = false;
				}
				mLastMotionX = 0;
				mLastMotionY = 0;
				startX = (int) 0;
				if (isClick) {
					showOrHide();
				}
				isClick = true;
				break;

			default:
				break;
			}
			return true;
		}

	};
	private IVideoOperateListener mVideoOperateListener;
	private IVideoPlayListener mPlayListener;
	private int mPlayTime;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(videoWidth, widthMeasureSpec);
		int height = getDefaultSize(videoHeight, heightMeasureSpec);
		if (videoWidth > 0 && videoHeight > 0) {
			if (videoWidth * height > width * videoHeight) {
				height = width * videoHeight / videoWidth;
			} else if (videoWidth * height < width * videoHeight) {
				width = height * videoWidth / videoHeight;
			}
		}
		setMeasuredDimension(width, height);
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
			setVideoPath(url);
		}
		requestFocus();
		onLoading_();
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
	
	protected void showOrHide() {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onClick();
		}
	}

	protected void backward(float absDeltaX) {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onBackward(absDeltaX);
		}
	}

	protected void forward(float absDeltaX) {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onForward(absDeltaX);
		}
	}

	protected void volumeUp(float absDeltaY) {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onVoiceUp(absDeltaY);
		}
	}

	protected void volumeDown(float absDeltaY) {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onVoiceDown(absDeltaY);
		}
	}

	protected void lightUp(float absDeltaY) {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onLightUp(absDeltaY);
		}
	}

	protected void lightDown(float absDeltaY) {
		if(mVideoOperateListener != null){
			mVideoOperateListener.onVoiceDown(absDeltaY);
		}
	}

	public void setOnOperateListener(IVideoOperateListener listener){
		mVideoOperateListener = listener;
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
	public void onPlayProgress_(int progress,int maxProgress,int bufProgress){
		if(mPlayListener != null){
			mPlayListener.onPlayProgress(progress,maxProgress<0?0:maxProgress,bufProgress);
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
	
	public void onError_(int errorType){
		Log.i(TAG, "出错了 onError_"+errorType);
		if(mPlayListener != null){
			mPlayListener.onError(errorType);
		}
	}
	
	public void onBlock(boolean isBlockFinish){
		Log.i(TAG, "出错了 onBlock");

		if(mPlayListener != null){
			mPlayListener.onBlock(isBlockFinish);
		}
	}
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
	
	public void setSeekToTime(int time){
		mPlayTime = time;
	}
	
	public void setWidthAndHeight(float width1,float height1){
		width = width1;
		height = height1;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		onPlayStart_();
		setVideoWidth(mp.getVideoWidth());
		setVideoHeight(mp.getVideoHeight());
		if(mPlayTime != 0){
			seekTo(mPlayTime);
		}
		start();
		showOrHide();
		onPlayProgress_(getCurrentPosition(),getDuration(), getBufferPercentage());
		mHandler.sendEmptyMessageDelayed(1, 1000);
//		mTimer = new Timer();
//		mTimer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				mHandler.sendEmptyMessage(1);
//			}
//		}, 0, 1000);
	}
	
	@Override
	public void stopPlayback() {
		super.stopPlayback();
		if(mTimer != null){
			mTimer.cancel();
		}
		mHandler.removeMessages(1);
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
	private Timer mTimer;
	@Override
	public void onCompletion(MediaPlayer mp) {
		onPlayCompletion_();
	}

	/**
	 * 播放出错的回掉
	 */
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		onError_(what);
		return false;
	}
	//播放卡的监听

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
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
			

		default:
			break;
		}
		
		return false;
	}

}
