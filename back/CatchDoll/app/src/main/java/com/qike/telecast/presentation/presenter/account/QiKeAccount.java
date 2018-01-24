package com.qike.telecast.presentation.presenter.account;

import android.content.Context;
import android.content.Intent;


import com.qike.telecast.presentation.application.CdApplication;
import com.qike.telecast.presentation.presenter.login.LoginPresenter;

import java.io.File;


/**
 * <p>7k7k账号操作</p><br/>
 *
 * @author xky
 * @since 1.0.0
 */
public class QiKeAccount implements IAccount {
    private LoginPresenter mLoginPresenter;
//    private RegistPresenter mRegisterPresenter;

    public QiKeAccount(Context context) {
//        mLoginPresenter = new LoginPresenter(context);
//        mRegisterPresenter = new RegistPresenter(context);
    }


    public void submitPic(String type, File file, IAccountPresenterCallBack callBack) {
        createRegisterPresenter();
//        mRegisterPresenter.submitPic(type, file, callBack);
    }


    /**
     * <p>账号登陆</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @author sll
     * @since 1.0.0
     */
    @Override
    public void login(Object... objs) {
        String type = (String) objs[0];
        String username = (String) objs[1];
        String pwd = (String) objs[2];
        IAccountPresenterCallBack mLoginBack = (IAccountPresenterCallBack) objs[3];
        createLoginPresenter();
        mLoginPresenter.login(type, username, pwd, mLoginBack);
    }

    private void createLoginPresenter() {
        if (mLoginPresenter == null) {
            mLoginPresenter = new LoginPresenter(CdApplication.getApplication());
        }
    }

    private void createRegisterPresenter() {
//        if (mRegisterPresenter == null) {
//            mRegisterPresenter = new RegistPresenter(QikeApplication.getApplication());
//        }
    }

    public void clearLoginPresneter() {
        mLoginPresenter = null;
    }

    public void clearRegisterPresenter() {
//        mRegisterPresenter = null;
    }

    /**
     * 第三方登陆
     *
     * @param loginType
     * @param mLoginBack
     * @param context
     */
    public void login(AccountManager.LoginType loginType, IAccountPresenterCallBack mLoginBack, Context context) {
        createLoginPresenter();
        mLoginPresenter.login(loginType, context);
        mLoginPresenter.registLoginPresenterCallBack(mLoginBack);
    }


    public void checkPhone(String phonenum, IAccountPresenterCallBack callBack) {
        createRegisterPresenter();
//        mRegisterPresenter.checkPhoneNum(phonenum, callBack);

    }


    /**
     * <p>取消登陆</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void cancelLogin() {
        //	mLoginPresenter.cancelLogin();
    }


    /**
     * 注册
     *
     * @param objs
     * @author sll
     */
    @Override
    public void register(Object... objs) {

        String userid = (String) objs[0];
        String pwd = (String) objs[1];
        String verifyCode = (String) objs[2];

        IAccountPresenterCallBack registCallBack = (IAccountPresenterCallBack) objs[3];
//		mRegisterPresenter.registRegistPresenterCallBack(registCallBack);
//		mRegisterPresenter.regist(userid,verifyCode, pwd);
    }

    public void registe(String mobile, String pwd, String nick, String gender, String avatar_url, String code, IAccountPresenterCallBack registCallBack) {
        createRegisterPresenter();
//        mRegisterPresenter.regist(mobile, pwd, nick, gender, avatar_url, code, registCallBack);
    }


    @Override
    public void logout(Object... objs) {
//		ILoginoutListener mLoginoutListener = (ILoginoutListener) objs[0];//注销的回掉
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mLoginPresenter != null) {
            mLoginPresenter.onActivityResult(requestCode, resultCode, data);
        }

    }

}
