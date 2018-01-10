package com.fy.catchdoll.library.widgets.webview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;


import com.fy.catchdoll.library.utils.DisplayUtils;
import com.fy.catchdoll.library.utils.Loger;
import com.fy.catchdoll.library.widgets.webview.inter.IWebChromeProgressListener;
import com.fy.catchdoll.library.widgets.webview.inter.IWebClientListener;
import com.fy.catchdoll.library.widgets.webview.inter.IWebVewListener;

import java.io.File;


/**
 * <p>加载网页</p><br/>
 *
 * @author xky
 * @since 1.0.0
 */
public class MWebView extends WebView implements IWebChromeProgressListener, IWebClientListener {


    private MWebViewClient mWebviewClient;
    private MWebChromeClient mWebChromeClient;
    private IWebVewListener mWebviewListener;
    private ProgressBar mProgressbar;
    private WebSettings mSettings;
    private Context mContext;
    private Handler mHandler;
    private IJsShareListener mShareListener;
    private boolean isShowProgressBar = true;

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mHandler = new Handler();
        setingWebView();
        addProgressView(context);
        initClient();
        addClient();
        setListener();
    }

    /**
     * 隐藏掉进度条
     */
    public void hideProgressBar() {
        isShowProgressBar = false;
        mProgressbar.setVisibility(GONE);
    }

    /**
     * <p>往webview中增加一个进度条</p><br/>
     *
     * @param context
     * @author xky
     * @since 1.0.0
     */
    private void addProgressView(Context context) {
        mProgressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 5, 0, 0));
        mProgressbar.setVisibility(View.GONE);
        addView(mProgressbar);
    }

    private void setListener() {
        mWebChromeClient.setOnProgressListener(this);
        mWebviewClient.setOnWebClientListener(this);
        addJavascriptInterface(this, "h5view");
    }

    /**
     * <p>js中调用，分享分数[]</p><br/>
     * onclick="window.h5view.scoreToShare('100')"
     *
     * @param score
     * @author xky
     * @since 1.0.0
     */
    @JavascriptInterface
    public void scoreToShare(final String score) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
//				Toast.makeText(mContext, "score="+score, 0).show();
                if (mShareListener != null) {
                    mShareListener.onShareContent(score);
                }
            }
        });
    }

    /**
     * <p>增加Client</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    private void addClient() {
        setWebViewClient(mWebviewClient);
        setWebChromeClient(mWebChromeClient);
    }

    /**
     * <p>webviewClient的初始化</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    private void initClient() {
        mWebviewClient = new MWebViewClient();
        mWebChromeClient = new MWebChromeClient();
    }

    /**
     * <p>对webview的一些设置</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    private void setingWebView() {
        mSettings = getSettings();
//		mSettings.setJavaScriptEnabled(true);
//		mSettings.setDefaultFixedFontSize(5);
//		mSettings.setBuiltInZoomControls(false);
//		mSettings.setGeolocationEnabled(true);
//		/**add**/
//		mSettings.setDomStorageEnabled(true);
//		mSettings.setAppCacheEnabled(true);
//		mSettings.setDatabaseEnabled(true);
//		mSettings.setAppCachePath(PathManager.getCachePath(mContext));
//		mSettings.setAppCacheMaxSize(1024*1024*8);
        /**add2*/
        mSettings.setPluginState(WebSettings.PluginState.ON);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setSaveFormData(true);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setCacheMode(-1);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**提高web的加载速度*/
//		mSettings.setRenderPriority(RenderPriority.HIGH);
//		mSettings.setBlockNetworkImage(true);
        /**自适应屏幕*/
//		mSettings.setUseWideViewPort(true);
//		mSettings.setLoadWithOverviewMode(true);

        String userAgentString = mSettings.getUserAgentString();
        String agent = userAgentString + "Feiyun/" + DisplayUtils.getAppVersionNameNoV(mContext);
//		try {
//			String name = DeviceUtil.getVersionName(mContext);
//			agent = agent+name;
//			LogManager.getInstance().showLog(Leve.I, MWebView.class, "name = "+agent.equals(userAgentString+" qikegameplayer/1.0"));
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
        mSettings.setUserAgentString(agent);


    }


    /**
     * <p>不使用缓存,只请求网络</p><br/>
     * 如果没有网络直接404
     *
     * @author xky
     * @since 1.0.0
     */
    public void setNoCache() {
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /**
     * <p>只读取本地缓存，不读取网络数据</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void setOnlyCache() {
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);

    }

    /**
     * <p>优先使用缓存，如果缓存不存在在请求网络</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void setCache() {
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    /**
     * <p>根据cache-control来决定是否使用缓存</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void setDefaultCache() {
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    /**
     * <p>清楚webview的缓存【未验证】</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void cleareCache() {
        clearCache(true);
    }

    /**
     * <p>清楚cache下的缓存文件和webview的数据库</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void cleareCacheFile() {
        File file = mContext.getCacheDir();
        if (file != null && file.exists() && file.isDirectory()) {
            for (File f : file.listFiles()) {
                f.delete();
            }
        }
        mContext.deleteDatabase("webview.db");
        mContext.deleteDatabase("webviewCache.db");
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressbar.getLayoutParams();//始终在webview的上方
        lp.x = l;
        lp.y = t;
        mProgressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setOnWebViewListener(IWebVewListener webviewListener) {
        mWebviewListener = webviewListener;
    }

    @Override
    public void onWebStart() {
        if (mWebviewListener != null) {
            mWebviewListener.onWebStart();
        }
    }

    @Override
    public void onWebFinish() {
        if (mWebviewListener != null) {
            mWebviewListener.onWebFinish();
        }
//		mSettings.setBlockNetworkImage(false);
    }

    @Override
    public void onWebReceiveError(int errorCode) {
        if (mWebviewListener != null) {
            mWebviewListener.onWebReceiveError(errorCode);
        }
    }

    @Override
    public void onProgress(int progress) {
        if (isShowProgressBar) {
            if (progress == 100) {
                mProgressbar.setVisibility(GONE);
            } else {
                if (mProgressbar.getVisibility() == GONE)
                    mProgressbar.setVisibility(VISIBLE);
                mProgressbar.setProgress(progress);
            }
        }
        if (mWebviewListener != null) {
            mWebviewListener.onProgress(progress);
        }
        if (progress == 100) {
            if (mWebviewListener != null) {
                mWebviewListener.onWebFinish();
            }
        }
    }


    public interface IJsShareListener {
        public void onShareContent(String content);
    }

    public void setOnJsShareListener(IJsShareListener shareListener) {
        mShareListener = shareListener;
    }

    @Override
    public void onLoadUrl(String url) {
        Loger.d("url---onLoadUrl------isDestroy------" + isDestroy);
        if (mWebviewListener != null && !isDestroy) {
            mWebviewListener.onLoadUrl(url);
        }
    }

    @Override
    public void loadUrl(String url) {
        Loger.d("url---loadUrl------isDestroy------" + isDestroy);
        if (isDestroy) return;
        super.loadUrl(url);
    }

    private boolean isDestroy = false;

    @Override
    public void destroy() {
        isDestroy = true;
        Loger.d("url---destroy------isDestroy------" + isDestroy);
        super.destroy();
    }

    /**
     * 关掉的时候需要清除的状态
     */
    public void onDestory() {
        mContext = null;
    }
}
