package com.fy.catchdoll.library.widgets.webview;

import android.webkit.WebStorage;
import android.webkit.WebView;

import com.fy.catchdoll.library.widgets.webview.inter.IWebChromeProgressListener;


/**
 * 
 *<p>自定义ChromeClient</p><br/>
 *主要是用来加载进度的
 * @since 1.0.0
 * @author xky
 */
public class MWebChromeClient extends android.webkit.WebChromeClient{
	
	private IWebChromeProgressListener mProgressListener;

	public MWebChromeClient() {
	}
	/**
	 * 加载网页的进图
	 */
	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		if(mProgressListener != null){
			mProgressListener.onProgress(newProgress);
		}
		super.onProgressChanged(view, newProgress);
	}
	
	public void setOnProgressListener(IWebChromeProgressListener progressListener){
		mProgressListener = progressListener;
	}
	
	@Override 
	public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) { 
	quotaUpdater.updateQuota(spaceNeeded * 2); 
	} 
}
