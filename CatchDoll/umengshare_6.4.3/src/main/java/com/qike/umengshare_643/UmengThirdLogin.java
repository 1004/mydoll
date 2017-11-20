package com.qike.umengshare_643;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.qike.umengshare_643.bean.UmengUserInfo;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 第三方登陆
 * Created by yb on 2016/8/29.
 */
public class UmengThirdLogin {

    private SHARE_MEDIA mShareMedia;

    private UmengUserInfo mUserInfo;

    private OnLoginListener mOnLoginListener;

    private Context mContext;

    private UMShareAPI mShareAPI;

    /**
     * <p>
     * TODO(第三方授权登陆)
     * </p>
     * <br/>
     * <p>
     * TODO(详细描述)
     * </p>
     *
     * @param share_media     登陆平台 SHARE_MEDIA.WEIXIN SHARE_MEDIA.SINA SHARE_MEDIA.QQ
     * @param onLoginListener 登陆回调
     * @author cherish
     * @since 1.0.0
     */
    public void doOauthLogin(SHARE_MEDIA share_media, OnLoginListener onLoginListener, Context context) {
        mContext = context;
        mShareMedia = share_media;
        mShareAPI = UMShareAPI.get(mContext);
        mOnLoginListener = onLoginListener;

        mUserInfo = new UmengUserInfo();
        switch (share_media) {
            case QQ:
                mUserInfo.setmPlat("QQ");
                break;
            case WEIXIN:
                mUserInfo.setmPlat("WEIXIN");
                break;
            case SINA:
                mUserInfo.setmPlat("SINA");
                break;
            default:
                break;
        }
        mShareAPI.doOauthVerify((Activity) context, share_media, umAuthListener);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 授权回调监听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(mContext.getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
            Set<String> key = data.keySet();
            Iterator<String> keyI = key.iterator();
            while (keyI.hasNext()) {
                String k = keyI.next();
                Log.e("test", "key=" + k + ";value=" + data.get(k));
            }
            switch (platform) {
                case WEIXIN:
                    mUserInfo.setToken(data.get("access_token"));
                    mUserInfo.setSource("wechat");
                    mUserInfo.setOpenId(data.get("unionid") + "," + data.get("openid"));
                    break;
                case QQ:
                    mUserInfo.setToken(data.get("access_token"));
                    mUserInfo.setSource("qq");
                    mUserInfo.setOpenId(data.get("openid"));
                    break;
                case SINA:

                    mUserInfo.setToken(data.get("access_token"));
                    mUserInfo.setSource("sina");
                    mUserInfo.setOpenId(data.get("uid"));
                    break;
                default:
                    break;
            }

            if (mOnLoginListener != null) {
                mOnLoginListener.onLoginSucceed(mUserInfo);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (mOnLoginListener != null) {
                mOnLoginListener.onLoginFailed(mUserInfo);
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (mOnLoginListener != null) {
                mOnLoginListener.onLoginCancle(mUserInfo);
            }
        }
    };

    public static interface OnLoginListener {

        public void onLoginSucceed(UmengUserInfo userInfo);

        public void onLoginFailed(UmengUserInfo userInfo);

        public void onLoginCancle(UmengUserInfo userInfo);
    }
}
