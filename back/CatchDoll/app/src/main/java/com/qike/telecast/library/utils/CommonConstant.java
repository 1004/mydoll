package com.qike.telecast.library.utils;

import android.app.Activity;
import android.content.Context;

import com.qike.telecast.presentation.application.CdApplication;


/**
 * 常用常量
 * Created by yb on 2017/5/27.
 */
public class CommonConstant {
    public static int screenWidth = 0;//在StartActivity中初始化
    public static int screenHeight = 0;//在StartActivity中初始化
    public static int statusBarHeight = 0;//在StartActivity中初始化  状态栏高度

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String PAGETYPE = "pageType";

    public static int getScreenWidth(Context context) {
        if (screenWidth <= 0) {
            if (context != null && context instanceof Activity) {
                screenWidth = Device.getScreenWidthAndHeight((Activity) context)[0];
            }
//            else if (MainActivity.mMainActivity != null) {
//                screenWidth = Device.getScreenWidthAndHeight(MainActivity.mMainActivity)[0];
//            }
        }
        return screenWidth;
    }

    public static int getScreenHeight(Context context) {
        if (screenHeight <= 0) {
            if (context != null && context instanceof Activity) {
                screenHeight = Device.getScreenWidthAndHeight((Activity) context)[1];
            }
//            else if (MainActivity.mMainActivity != null) {
//                screenHeight = Device.getScreenWidthAndHeight(MainActivity.mMainActivity)[1];
//            }
        }
        return screenHeight;
    }

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight <= 0) {
            if (context != null) {
                statusBarHeight = Device.getStatusBarHeight(context);
//            }
//            else if (MainActivity.mMainActivity != null) {
//                statusBarHeight = Device.getStatusBarHeight(MainActivity.mMainActivity);
            } else if (CdApplication.getApplication() != null) {
//                statusBarHeight = Device.getStatusBarHeight(GvApplication.getApplication());
            }
        }
        return statusBarHeight;
    }

}
