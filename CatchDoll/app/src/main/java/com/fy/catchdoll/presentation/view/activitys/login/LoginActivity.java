package com.fy.catchdoll.presentation.view.activitys.login;

import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

/**
 * Created by xky on 2017/11/20 0020.
 */
public class LoginActivity extends AppCompatBaseActivity {
    private View mLoginBtn;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mLoginBtn = findViewById(R.id.login_wx);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.login_wx:
                ActivityUtils.startMainActivity(this);
                break;
        }
    }
}
