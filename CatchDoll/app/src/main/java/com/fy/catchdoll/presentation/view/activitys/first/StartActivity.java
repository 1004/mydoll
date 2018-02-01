package com.fy.catchdoll.presentation.view.activitys.first;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.module.support.permission.OnRequestPermissionsCallBack;
import com.fy.catchdoll.module.support.permission.PermissionCompat;
import com.fy.catchdoll.presentation.presenter.start.StartPresenter;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;


/**
 * Created by wst on 2017/12/2.
 *
 * 启动页
 */
public class StartActivity extends AppCompatBaseActivity implements StartPresenter.OnRequestPermissonCallBack {
    private Handler mHandler;
    private StartPresenter mPresenter;

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
        mPresenter = new StartPresenter(this);
    }

    @Override
    public void setListener() {
        mPresenter.setRequestPermissionCalLBack(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.initPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            ActivityUtils.startLoginActivity(StartActivity.this);
            finish();
        }
    };

    @Override
    public void onNo() {
        mHandler.postDelayed(mTask,2000);
    }

    @Override
    public void onSure() {
        mHandler.postDelayed(mTask,2000);
    }
}
