package com.fy.catchdoll.presentation.view.activitys.login;

import android.content.Intent;
import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ActivityUtils;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.presenter.ErrorCodeOperate;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.account.IAccountPresenterCallBack;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

/**
 * Created by xky on 2017/11/20 0020.
 */
public class LoginActivity extends AppCompatBaseActivity implements IAccountPresenterCallBack {
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
        User user = AccountManager.getInstance().getUser();
        if (user != null){
            //已经登陆过
            startMainActivity();
        }
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
        switch (v.getId()) {
            case R.id.login_wx:
//                ActivityUtils.startMainActivity(this);
                AccountManager.getInstance().login(AccountManager.LoginType.WEIXIN,this,this);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {// 防止取消三方授权回来后Crash //Yocn
            AccountManager.getInstance().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void callBackStats(int status) {

    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        startMainActivity();
    }

    private void startMainActivity(){
        ActivityUtils.startMainActivity(this);
        finish();
    }

    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError("",this,code,msg,true);
    }
}
