package com.fy.catchdoll.presentation.view.activitys.login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    private View mImgContainer;
    private ImageView mAgreeImg;
    private boolean isCheck = true;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mLoginBtn = findViewById(R.id.login_wx);
        mImgContainer = findViewById(R.id.agree_img_container);
        mAgreeImg = (ImageView) findViewById(R.id.agree_img);
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
        findViewById(R.id.agree_tv).setOnClickListener(this);
        mImgContainer.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_wx:
                if (!isCheck){
                    Toast.makeText(this,getMString(R.string.login_unagree_hint),Toast.LENGTH_SHORT).show();
                    return;
                }
                AccountManager.getInstance().login(AccountManager.LoginType.WEIXIN,this,this);
                break;
            case R.id.agree_tv:
                ActivityUtils.startWebActivity(this,"http://www.baidu.com","用户协议");
                break;
            case R.id.agree_img_container:
                operateImgCheck();
                break;
        }
    }

    private void operateImgCheck() {
        isCheck = !isCheck;
        mAgreeImg.setImageResource(isCheck ? R.mipmap.xuanzhong : R.mipmap.weixuanzhong);
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
