package com.fy.catchdoll.presentation.view.activitys.first;

import android.os.Handler;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;


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
