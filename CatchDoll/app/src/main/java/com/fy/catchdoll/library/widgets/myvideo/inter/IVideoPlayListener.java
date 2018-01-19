package com.fy.catchdoll.library.widgets.myvideo.inter;
/**
 * 
 *<p>视频播放的回掉</p><br/>
 * @since 1.0.0
 * @author xky
 */
public interface IVideoPlayListener {
	/**视频加载中....**/
	public void onLoading();
	/**视频加载完成开始播放**/
	public void onPlay();
	/**视频播放进度*/
	public void onPlayProgress(int progress, int maxProgress, int bufprogress);
	/**视频播放完成*/
	public void onPlayCompletion();
	/***暂停播放*/
	public void onPause();
	/**播放出错
	 * @param errorType */
	public void onError(int errorType);
	/**播放卡的监听*/
	public void onBlock(boolean isBlockFinish);
}
