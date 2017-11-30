package com.fy.catchdoll.presentation.model.business.login;



import com.fy.catchdoll.module.network.BazaarGetDao;
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
    private BazaarGetDao<User> mThirdDao;

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
        if (mLoginBizCallBack != null) {
            mLoginBizCallBack.dataResult("token="+token+"appid=",null,0);
        }
//        if (source.equals("qq")) {
//            mThirdDao = new BazaarGetDao<User>(Paths.BASEPATH + Paths.THIRD_LOGIN, User.class, BazaarGetDao.ARRAY_DATA_CHUNK);
//        } else {
//            mThirdDao = new BazaarGetDao<User>(Paths.BASEPATH + Paths.WEIXIN_LOGIN, User.class, BazaarGetDao.ARRAY_DATA_CHUNK);
//        }
//
//        mThirdDao.setNoCache();
//        mThirdDao.registerListener(new BaseLoadListener() {
//            @Override
//            public void onComplete(IDao.ResultType resultType) {
//                super.onComplete(resultType);
//                if (mLoginBizCallBack != null) {
//                    if (mThirdDao.getStatus() == 200) {
//                        mLoginBizCallBack.dataResult(mThirdDao.getData());
//                        mLoginBizCallBack.callBackStats(mThirdDao.getStatus());
//
//                    } else {
//                        if (mThirdDao.getErrorData() != null) {
//                            mLoginBizCallBack.errerResult(mThirdDao.getErrorData().getCode(), mThirdDao.getErrorData().getMsg());
//                        } else {
//                            mLoginBizCallBack.errerResult(mThirdDao.getStatus(), "");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Result result) {
//                super.onError(result);
//                if (mLoginBizCallBack != null) {
//                        mLoginBizCallBack.errerResult(result.getCode(),result.getErrmsg());
//                }
//            }
//        });
//        if (source.equals("qq")) {
//            mThirdDao.putParams("token", token);
//            mThirdDao.putParams("open_id", openId);
//        } else {
//            mThirdDao.putParams("token", token);
//            mThirdDao.putParams("open_id", openId);
//        }
//        mThirdDao.putParams("source", source);
//        mThirdDao.putParams("appid", 3);
//        mThirdDao.asyncDoAPI();

    }
}
