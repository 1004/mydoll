package com.fy.catchdoll.library.widgets.webview;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;

import com.fy.catchdoll.library.widgets.webview.inter.IWebClientListener;
import com.fy.catchdoll.presentation.view.activitys.web.WebActivity;


/**
 * 
 *<p>自定义webviewclient</p><br/>
 *主要是用来处理webview的事件的
 * @since 1.0.0
 * @author xky
 */
public class MWebViewClient extends android.webkit.WebViewClient{
	private IWebClientListener mClientListener;

	public MWebViewClient() {
	}
	/**
	 * 处理掉点击事件
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
//		if (url.contains("shareqq")) {
//			WebActivity.shareWeb();
//			return false;
//		}
//		if (WebActivity.checkWebPay(url) || WebActivity.checkWebPayFinishBtn(url)){
//
//		}else {
//			view.loadUrl(url);
//		}

		view.loadUrl(url);

		if(mClientListener != null){
        	mClientListener.onLoadUrl(url);
			Log.e("TAG", "shouldOverrideUrlLoading==" + url);
        }
        return true;
	}
	/**
	 * 当请求网页出错【404】
	 */
	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			if(mClientListener != null){
				mClientListener.onWebReceiveError(errorCode);
			}
		String	errorHtml = "<html><body><h1></h1></body></html>";
		view.loadData(errorHtml, "text/html", "UTF-8");
//		super.onReceivedError(view, errorCode, description, failingUrl);
	}
	/**
	 * 加载页面时候的回掉
	 */
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		if(mClientListener != null){
			mClientListener.onWebStart();
		}
	}
	/**
	 * 加载页面结束的时候调用
	 */
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		if(mClientListener != null){
			mClientListener.onWebFinish();
		}
	}
	
	public void setOnWebClientListener(IWebClientListener clientListener){
		mClientListener = clientListener;
	}
}
