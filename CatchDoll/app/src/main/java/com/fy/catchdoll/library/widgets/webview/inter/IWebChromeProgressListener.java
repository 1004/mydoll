package com.fy.catchdoll.library.widgets.webview.inter;
/**
 * 
 *<p>网页加载进度的回掉</p><br/>
 * @since 1.0.0
 * @author xky
 */
public interface IWebChromeProgressListener {
	/**
	 * 
	 *<p>进度的回掉</p><br/>
	 * @since 1.0.0
	 * @author xky
	 * @param progress 【1-100】
	 */
	public void onProgress(int progress);
}
