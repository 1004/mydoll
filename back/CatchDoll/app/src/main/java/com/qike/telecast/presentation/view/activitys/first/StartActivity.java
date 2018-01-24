package com.qike.telecast.presentation.view.activitys.first;

import android.os.Handler;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.ActivityUtils;
import com.qike.telecast.presentation.view.activitys.base.AppCompatBaseActivity;


/**
 * Created by wst on 2017/12/2.
 *
 * 启动页
 */
public class StartActivity extends AppCompatBaseActivity{
    private Handler mHandler;

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initView() {

    }

    @Override
    protected boolean isImmersionBar() {
        return false;
    }

    @Override
    public void initData() {
        mHandler = new Handler();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void loadData() {
        mHandler.postDelayed(mTask,2000);
    }

    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            ActivityUtils.startLoginActivity(StartActivity.this);
            finish();
        }
    };
}
