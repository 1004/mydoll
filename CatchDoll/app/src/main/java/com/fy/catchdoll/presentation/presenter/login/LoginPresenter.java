package com.fy.catchdoll.presentation.presenter.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.IAccountBizCallBack;
import com.fy.catchdoll.presentation.model.business.login.LoginBiz;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;
import com.fy.catchdoll.presentation.presenter.account.AccountStoreManager;
import com.fy.catchdoll.presentation.presenter.account.IAccountPresenterCallBack;
import com.qike.umengshare_643.UmengThirdLogin;
import com.qike.umengshare_643.bean.UmengUserInfo;
import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * <p>登陆presenter</p><br/>
 * <p>处理登陆的逻辑 包括登陆后对数据的存储处理</p>
 *
 * @author sll
 * @since 1.0.0
 */
public class LoginPresenter implements IAccountBizCallBack {
    private LoginBiz mLoginBiz;
    private IAccountPresenterCallBack mLoginPresenterCallBack;
    private Context mContext;
    private UmengThirdLogin thirdLogin;
    private UmengUserInfo mUmengUserInfo;
    private AccountManager.LoginType mLoginType;

    public LoginPresenter(Context context) {
        mContext = context;
        mLoginBiz = new LoginBiz();
        mLoginBiz.registLoginBizCallBack(this);
        thirdLogin = new UmengThirdLogin();
    }

    /**
     * 账号登陆
     *
     * @param username
     * @param pwd
     * @author sll
     */
    public void login(String type, String username, String pwd, final IAccountPresenterCallBack callBack) {

        mLoginBiz.login(type, username, pwd, new IAccountBizCallBack() {

            @Override
            public void dataResult(Object obj, Page page, int status) {
                if (obj != null && obj instanceof User) {
                    User user = (User) obj;
                    if (!TextUtils.isEmpty(user.getId())) {
                        AccountStoreManager.getInstance().saveUser(user);
                    }
                }
                if (callBack != null) {
                    callBack.dataResult(obj,page,status);
                }
            }

            @Override
            public void errerResult(int code, String msg) {
                if (callBack != null) {
                    callBack.errerResult(code,msg);
                }
            }


            @Override
            public void callBackStats(int status) {
                if (callBack != null) {
                    callBack.callBackStats(status);
                }
            }
        });
    }

    /**
     * 第三方登陆
     *
     * @param loginType
     * @param context
     */
    public void login(AccountManager.LoginType loginType, Context context) {
        mLoginType = loginType;
        switch (loginType) {
            case WEIXIN:
                thirdLogin.doOauthLogin(SHARE_MEDIA.WEIXIN, mOnLoginListener, context);
                break;
            case QQ:
                thirdLogin.doOauthLogin(SHARE_MEDIA.QQ, mOnLoginListener, context);
                break;
            case WEIBO:
                thirdLogin.doOauthLogin(SHARE_MEDIA.SINA, mOnLoginListener, context);
                break;
            default:
                break;
        }
    }

    public void registLoginPresenterCallBack(IAccountPresenterCallBack callBack) {
        mLoginPresenterCallBack = callBack;
    }


    @Override
    public void callBackStats(int status) {
        // TODO Auto-generated method stub
        if (mLoginPresenterCallBack != null) {
            mLoginPresenterCallBack.callBackStats(status);
        }
    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
                if (obj != null && obj instanceof User) {
            User user = (User) obj;
            if (!TextUtils.isEmpty(user.getId())) {
                AccountStoreManager.getInstance().saveUser(user);
                Log.d("login_user_storage", AccountManager.getInstance().getUser().toString());
            }

        }
        if (mLoginPresenterCallBack != null) {
            mLoginPresenterCallBack.dataResult(obj,null,0);
        }
    }

    @Override
    public void errerResult(int code, String msg) {
        if (mLoginPresenterCallBack != null) {
            mLoginPresenterCallBack.errerResult(code,msg);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (thirdLogin != null) {
            thirdLogin.onActivityResult(requestCode, resultCode, data);
        }

    }

    /**
     * 第三方授权登陆的回调
     */
    private UmengThirdLogin.OnLoginListener mOnLoginListener = new UmengThirdLogin.OnLoginListener() {

        @Override
        public void onLoginSucceed(UmengUserInfo userInfo) {
            mUmengUserInfo = userInfo;
            String getmPlat1 = mUmengUserInfo.getmPlat();
            Log.e("TAG", "getmPlat==" + getmPlat1);
            Log.e("TAG", "mUmengUserInfo==" + mUmengUserInfo.getSource());
            Log.e("TAG", "token==" + mUmengUserInfo.getToken());
            Log.e("TAG", "openid==" + mUmengUserInfo.getOpenId());
//            Toast.makeText(mContext, R.string.auoth_success, Toast.LENGTH_SHORT).show();
            loginThird();
        }

        @Override
        public void onLoginFailed(UmengUserInfo userInfo) {
            mUmengUserInfo = userInfo;
            Toast.makeText(mContext, R.string.auoth_failed, Toast.LENGTH_SHORT).show();
            String getmPlat2 = mUmengUserInfo.getmPlat();

            mUmengUserInfo.setOpenId("oXf5E08GF16ghK1mfD7rny7Rr1Pw");
            mUmengUserInfo.setToken("4_i1liyMakV4hRuyaaWuzaZUdTadbdbN82tTEfJD1CvpvL3yHAXHW7bUpVAs8xNyd9uh01IJ-MsRfmUUqbgPFJq9XpKsb6zDseGlOeiiM-nWM");
            loginThird();
        }

        @Override
        public void onLoginCancle(UmengUserInfo userInfo) {
            mUmengUserInfo = userInfo;
            if (mUmengUserInfo != null) {
                String getmPlat3 = mUmengUserInfo.getmPlat();
            }
            Toast.makeText(mContext, R.string.auoth_cancle, Toast.LENGTH_SHORT).show();
            mUmengUserInfo.setOpenId("oXf5E08GF16ghK1mfD7rny7Rr1Pw");
            mUmengUserInfo.setToken("4_i1liyMakV4hRuyaaWuzaZUdTadbdbN82tTEfJD1CvpvL3yHAXHW7bUpVAs8xNyd9uh01IJ-MsRfmUUqbgPFJq9XpKsb6zDseGlOeiiM-nWM");
            loginThird();

        }
    };

    private void loginThird() {
        if (mUmengUserInfo != null) {
            mLoginBiz.loginThird(mUmengUserInfo.getToken(), mUmengUserInfo.getOpenId(), mUmengUserInfo.getSource());
        }
    }
}
