package com.fy.catchdoll.presentation.presenter.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.fy.catchdoll.presentation.application.CdApplication;
import com.fy.catchdoll.presentation.model.dto.account.User;

import java.io.File;
import java.util.List;


/**
 * <p>账号管理器</p><br/>
 *
 * @author xky
 * @since 1.0.0
 */
public class AccountManager {
    private static AccountManager INSTANCE = null;
    private QiKeAccount mAccount;
    private User mUser;
    private Context context;

    private LoginType mLoginType = LoginType.NORMAL;


    private AccountManager() {
        context = CdApplication.getApplication();
        mAccount = new QiKeAccount(context);
    }

    public static synchronized AccountManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AccountManager();
        }
        return INSTANCE;
    }


    /**
     * <p>普通注册</p><br/>
     *
     * @author sll
     * @since 1.0.0
     */
    public void regist(String mobile, String pwd, String nick, String gender, String avatar_url, String code, IAccountPresenterCallBack registCallBack) {
        mAccount.registe(mobile, pwd, nick, gender, avatar_url, code, registCallBack);
    }

    /**
     * <p>手动登录</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void login(String type, String username, String pwd, IAccountPresenterCallBack mLoginBack) {
        mAccount.login(type, username, pwd, mLoginBack);
        if (username.matches("[0-9]+")) {
            setmLoginType(LoginType.NORMAL);
        } else {
            setmLoginType(LoginType.ACCOUNT);
        }
    }

    public void clearAccountLoginPresneter() {
        mAccount.clearLoginPresneter();
    }

    public void clearAccountRegisterPresneter() {
        mAccount.clearRegisterPresenter();
    }

    /**
     * <p>
     * TODO(第三方登陆)
     * </p>
     * <br/>
     * <p>
     * TODO(详细描述)
     * </p>
     *
     * @param loginType
     * @author cherish
     * @since 1.0.0
     */
    public void login(LoginType loginType, IAccountPresenterCallBack mLoginBack, Context context) {
        mAccount.login(loginType, mLoginBack, context);
        setmLoginType(loginType);
    }

    public void submitPic(String type, File file, IAccountPresenterCallBack callBack) {
        mAccount.submitPic(type, file, callBack);
    }


    /**
     * <p>注销</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void logout() {
        mUser = getUser();
        if (mUser != null) {
            mUser.setIslast(0);
            AccountStoreManager.getInstance().updateUser(mUser);
            mUser = null;
        }
    }

    /**
     * <p>获得当前的User</p><br/>
     *
     * @return 如果没有则为null
     * @author xky
     * @since 1.0.0
     */
    public User getUser() {
        if (mUser == null || TextUtils.isEmpty(mUser.getId())) {
            mUser = AccountStoreManager.getInstance().queryNewUser();
        }
        return mUser;
    }

    /**
     * <p>设置用户</p><br/>
     *
     * @param user
     * @author xky
     * @since 1.0.0
     */
    public void setUser(User user) {
        mUser = user;
        LoginNotifyManager.getInstance().notifyLoginChange();
    }

    /**
     * <p>删除具体的用户信息</p><br/>
     *
     * @param user
     * @author xky
     * @since 1.0.0
     */
    public void deleteUser(User user) {
        AccountStoreManager.getInstance().deleteUser(user);
    }

    /**
     * <p>修改用户信息存储</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    public void updateUser(User user) {
        AccountStoreManager.getInstance().updateUser(user);
        mUser = AccountStoreManager.getInstance().queryNewUser();
    }

    /**
     * <p>获取所有的用户登录记录</p><br/>
     *
     * @return
     * @author xky
     * @since 1.0.0
     */
    public List<User> getAllUser() {
        return AccountStoreManager.getInstance().queryUsers();
    }


    public static enum LoginType {
        WEIXIN, WEIBO, QQ, NORMAL, ACCOUNT
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mAccount != null) {
            mAccount.onActivityResult(requestCode, resultCode, data);
        }
    }

    public LoginType getmLoginType() {
        return mLoginType;
    }

    public void setmLoginType(LoginType mLoginType) {
        this.mLoginType = mLoginType;
    }

    public void checkPhone(String phone, IAccountPresenterCallBack callBack) {
        mAccount.checkPhone(phone, callBack);
    }
}
