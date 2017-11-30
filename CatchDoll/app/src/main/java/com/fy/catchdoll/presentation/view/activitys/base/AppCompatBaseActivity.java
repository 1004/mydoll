package com.fy.catchdoll.presentation.view.activitys.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;


import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.GlideUtils;
import com.fy.catchdoll.library.utils.UiUtils;
import com.fy.catchdoll.module.support.immersionbarlib.ImmersionBar;

import java.util.ArrayList;
import java.util.List;


/**
 * 所有Activity基类<br/>
 * 封装了Activity经常使用的方法
 *
 * @author suenxianhao
 * @since 5.0.0
 */
public abstract class AppCompatBaseActivity extends FragmentActivity implements OnClickListener, IViewOperater {


    /**
     * 程序是否处于前台，默认值false
     */
    private boolean isActive = true;
    protected ImmersionBar mImmersionBar;
    private TextView mTitleTv;

    /**
     * 监听程序从后台切换回来的接口
     *
     * @author suenxianhao
     * @since 5.0.0
     */
    public interface RestoreBackGroundListener {
        void onBack();
    }

    /**
     * 监听程序从后台切换回来的接口集合
     */
    public ArrayList<RestoreBackGroundListener> mRestoreBgListeners = new ArrayList<RestoreBackGroundListener>();

    /**
     * 添加监听程序从后台切换回来的接口
     */
    public void addRestoreBackGroundListener(RestoreBackGroundListener listener) {
        if (!mRestoreBgListeners.contains(listener)) {
            mRestoreBgListeners.add(listener);
        }
    }

    /**
     * 删除监听程序从后台切换回来的接口
     */
    public void removeRestoreBackGroundListener(RestoreBackGroundListener listener) {
        if (mRestoreBgListeners.contains(listener)) {
            mRestoreBgListeners.remove(listener);
        }


    }

    /**
     * 每个继承此类的activity都会将实例添加到activity集合中进行统一管理
     *
     * @author suenxianhao
     * @since 5.0.0
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(isVoriention() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        UiUtils.createActivity(this);
        super.onCreate(savedInstanceState);
        immersionBarInit();
        setContentView(getLayoutId());
        initView();
        initCommonNavView();
        initData();
        setListener();
        loadData();
        initUmengPush();
    }

    protected void initUmengPush() {
       // PushHelper.getInstance().openAppStart(this);

    }

    private void initCommonNavView(){
        View viewById = findViewById(R.id.bacK_common_nav);
        if (viewById != null){
            viewById.findViewById(R.id.nav_back).setOnClickListener(this);
            mTitleTv = (TextView) viewById.findViewById(R.id.nav_title);
        }
    }

    public void setCommonTitle(String titleStr){
        if (mTitleTv != null){
            mTitleTv.setText(titleStr);
        }
    }

    public boolean isVoriention() {
        return true;
    }

    public abstract int getLayoutId();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public Context getContext() {
        return this;
    }

    protected String getMString(int strId){
        return getResources().getString(strId);
    }
    /**
     * 在銷毀的同時，刪除其所在activity集合中的實例
     *
     * @author suenxianhao
     * @since 5.0.0
     */
    @Override
    protected void onDestroy() {
        UiUtils.destroyActivity(this);
        immersionBarDestroy();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //TODO	用户行为统计（从后台返回）
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//	@Override
//	protected void onResume() {
//		super.onResume();
//
//		/*		int upIconId =  getResources().getSystem().getIdentifier("up", "id", "android");
//				ImageView upIcon = (ImageView) findViewById(upIconId);
//				if(upIcon!=null){
//					upIcon.setImageResource(R.drawable.abc_ic_ab_back_holo_dark);
//				}
//
//				*/
//	}

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_back:
                finish();
                break;
        }
    }

    private long mExitTime;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onResume() {
        super.onResume();
        GlideUtils.resumeAllRequest(this);
    }

    public void onPause() {
        super.onPause();
        GlideUtils.pauseAllRequest(this);
    }

    /**--------------沉浸式状态栏开始---------------**/
    protected boolean isImmersionBar(){
        return true;
    }

    protected void immersionBarInit() {
        if (!isImmersionBar()||!ImmersionBar.isSupportStatusBarDarkFont()) {
            return;
        }
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarColor(getBarColor())
                .fitsSystemWindows(true)
                .statusBarDarkFont(isDarkFont());
        mImmersionBar.init();
    }

    protected int getBarColor(){
        return R.color.color_white;
    }

    protected boolean isDarkFont(){
        return true;
    }



    private void immersionBarDestroy() {
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }


}
