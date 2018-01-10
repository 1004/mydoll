package com.fy.catchdoll.library.widgets.webview.inter;
/**
 * 
 *<p>Webviewclient的回掉</p><br/>
 * @since 1.0.0
 * @author xky
 */
public interface IWebClientListener {
	/**
	 * 
	 *<p>页面开始加载</p><br/>
	 * @since 1.0.0
	 * @author xky
	 */
	public void onWebStart();
	/**
	 * 
	 *<p>页面加载完成</p><br/>
	 * @since 1.0.0
	 * @author xky
	 */
	public void onWebFinish();
	/**
	 * 
	 *<p>网页加载失败</p><br/>
	 * @since 1.0.0
	 * @author xky
	 * @param errorCode
	 */
	public void onWebReceiveError(int errorCode);
	
	public void onLoadUrl(String url);
}
