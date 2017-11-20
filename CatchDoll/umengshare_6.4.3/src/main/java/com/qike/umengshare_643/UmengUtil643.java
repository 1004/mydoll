package com.qike.umengshare_643;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by yb on 2017/3/27.
 */
public class UmengUtil643 {
    /**
     * 防止从开发者app调到qq或者微信的授权界面，后台把开发者app杀死了
     * 在使用的activity中重写onSaveInstanceState方法中调用
     *
     * @param context
     * @param outState
     */
    public static void onSaveInstanceState(Context context, Bundle outState) {
        UMShareAPI.get(context).onSaveInstanceState(outState);
    }

    /**
     * 防止从开发者app调到qq或者微信的授权界面，后台把开发者app杀死了
     * 在使用的activity中重写onCreate()方法中调用
     */
    public static void fetchListener(Activity context, Bundle saveInstanceState, UMAuthListener listener) {
        UMShareAPI.get(context).fetchAuthResultWithBundle(context, saveInstanceState, listener);
    }

    /**
     * 防止内存泄漏
     * 在使用分享或者授权的Activity中，重写onDestory()方法：
     *
     * @param context
     */
    public static void release(Context context) {
        UMShareAPI.get(context).release();
    }

    public static void deleteAuth(Activity context, SHARE_MEDIA media) {
        if (context == null || media == null) {
            return;
        }
        try {
            UMShareAPI.get(context).deleteOauth(context, media, null);
        } catch (Exception e) {
        }
    }
}
