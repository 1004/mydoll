package com.fy.catchdoll.presentation.model.business.login;



import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.BazaarPostDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.IAccountBizCallBack;
import com.fy.catchdoll.presentation.model.dto.account.User;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * <p>登录biz</p><br/>
 *
 * @author sll
 * @since 1.0.0
 */

public class LoginBiz {
    private IAccountBizCallBack mLoginBizCallBack;
    private BazaarGetDao<User> mDao;
    private BazaarPostDao<User> mThirdDao;


    public LoginBiz() {
    }

    /**
     * 登陆
     *
     * @param username
     * @param pwd
     * @author sll
     */
    public void login(String type, String username, String pwd, final IAccountBizCallBack loginBizCallBack) {

//        mDao = new BazaarGetDao<User>(Paths.NEWBASEPATH + Paths.LOGIN_MOBILE, User.class, BazaarGetDao.ARRAY_DATA_CHUNK);
//        mDao.setNoCache();
//        mDao.registerListener(new BaseLoadListener() {
//
//            @Override
//            public void onComplete(IDao.ResultType resultType) {
//                super.onComplete(resultType);
//                if (loginBizCallBack != null) {
//                    if (mDao.getStatus() == 200) {
//                        loginBizCallBack.dataResult(mDao.getData());
//                        loginBizCallBack.callBackStats(mDao.getStatus());
//                    } else {
//                        if (mDao.getErrorData() != null) {
//                            loginBizCallBack.errerResult(mDao.getErrorData().getCode(), mDao.getErrorData().getMsg());
//                        } else {
//                            loginBizCallBack.errerResult(mDao.getStatus(), "");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Result result) {
//                super.onError(result);
//                if (loginBizCallBack != null) {
//                    loginBizCallBack.errerResult(result.getCode(), result.getErrmsg());
//                }
//            }
//
//        });
//
//        mDao.putParams(type, username);
//        mDao.putParams("password", pwd);
//        mDao.setNoCache();
//        mDao.asyncDoAPI();
    }

    /**
     * 注册一个登陆的回调
     *
     * @param callBack
     * @author sll
     */
    public void registLoginBizCallBack(IAccountBizCallBack callBack) {
        mLoginBizCallBack = callBack;
    }

    public void loginThird(String token, String openId, String source) {

        mThirdDao = new BazaarPostDao<User>(Paths.NEWAPI + Paths.WX_LOGIN_DATA, User.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mThirdDao.setNoCache();
        mThirdDao.registerListener(new BaseLoadListener() {
            @Override
            public void onComplete(IDao.ResultType resultType) {
                super.onComplete(resultType);
                if (mLoginBizCallBack != null) {
                    mLoginBizCallBack.dataResult(mThirdDao.getData(), null, 0);
                }
            }

            @Override
            public void onError(Result result) {
                super.onError(result);
                if (mLoginBizCallBack != null) {
                    mLoginBizCallBack.errerResult(result.getCode(), result.getErrmsg());
                }
            }
        });
        mThirdDao.putParams("access_toen", token);
        mThirdDao.putParams("openid", openId);
        mThirdDao.asyncDoAPI();
    }
}
