package com.fy.catchdoll.presentation.view.activitys.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.widgets.webview.MWebView;
import com.fy.catchdoll.library.widgets.webview.inter.IWebVewListener;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xky on 2018/1/10 0010.
 */
public class WebActivity extends AppCompatBaseActivity implements IWebVewListener {
    private MWebView mWebView;
    private ImageView mIvRefresh;
    private String uploadUrl = "";
    public static final String KEY_URL = "key_url";
    public static final String KEY_NAME = "key_name";
    private LinearLayout mSelfLoading;
    private Timer timers;
    private MyTimerTask timerTasks;
    private int timehideconut = 2;
    private int timer_unit = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        mWebView = (MWebView) findViewById(R.id.mywebview);
        mIvRefresh = (ImageView) findViewById(R.id.iv_refresh);
        mSelfLoading = (LinearLayout) findViewById(R.id.webView_loading);

    }

    @Override
    public void initData() {
        mWebView.setOnWebViewListener(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(this, "wv");
    }

    @Override
    public void setListener() {
        mIvRefresh.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        String url = getIntent().getStringExtra(KEY_URL);
        String name = getIntent().getStringExtra(KEY_NAME);
        setCommonTitle(name);
        uploadUrl = url;
        if (mWebView != null && url != null) {
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.iv_refresh:
                mIvRefresh.setEnabled(false);
                mSelfLoading.setVisibility(View.VISIBLE);
                timers = new Timer();
                timerTasks = new MyTimerTask();
                timers.scheduleAtFixedRate(timerTasks, 0, 1000);
                break;
        }
    }


    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            timehideconut = timehideconut - timer_unit;
            if (timehideconut <= 0) {
                cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mWebView != null) {
                            mWebView.loadUrl(uploadUrl);
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView.onDestory();
            mWebView = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timerTasks != null) {
            timerTasks.cancel();
        }
    }


    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onWebStart() {

    }

    @Override
    public void onWebFinish() {
        mIvRefresh.setEnabled(true);
        mSelfLoading.setVisibility(View.GONE);
    }

    @Override
    public void onWebReceiveError(int errorCode) {
        mIvRefresh.setEnabled(true);
        mSelfLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoadUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.endsWith(".apk")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
