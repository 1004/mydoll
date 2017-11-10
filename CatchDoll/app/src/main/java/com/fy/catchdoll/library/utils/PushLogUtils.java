package com.fy.catchdoll.library.utils;

import android.util.Log;

/**
 * Created by xky on 2016/11/9 0009.
 */
public class PushLogUtils {

    private static boolean isPrintLog = true;//是否可以打印log

    public static void i(String tag, String message) {
        if (isPrintLog) {
            Log.i("gvideo"+tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (isPrintLog) {
            Log.w("gvideo"+tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (isPrintLog) {
            Log.e("gvideo"+tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (isPrintLog) {
            Log.v("gvideo"+tag, message);
        }
    }

}
