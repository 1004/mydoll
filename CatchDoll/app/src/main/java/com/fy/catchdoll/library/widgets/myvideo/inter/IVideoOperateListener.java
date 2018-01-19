package com.fy.catchdoll.library.widgets.myvideo.inter;
/**
 * 
 *<p>对视频一些操作的回掉</p><br/>
 *<p>亮度 声音 快进 快退</p>
 * @since 1.0.0
 * @author xky
 */
public interface IVideoOperateListener {
	/**
	 * 
	 *<p>亮度提高</p><br/>
	 * @since 1.0.0
	 * @author xky
	 */
	public void onLightUp(float value);
	/**亮度降低**/
	public void onLightDown(float value);
	/**声音提高**/
	public void onVoiceUp(float value);
	/**声音降低*/
	public void onVoiceDown(float value);
	/***快进*/
	public void onForward(float value);
	/**快退**/
	public void onBackward(float value);
	/**点击video*/
	public void onClick();
}
