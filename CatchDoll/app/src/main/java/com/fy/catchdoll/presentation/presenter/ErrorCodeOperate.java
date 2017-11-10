package com.fy.catchdoll.presentation.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.fy.catchdoll.R;


public class ErrorCodeOperate {
    /**
     * test
     */

    public static final int CODE_ERROR_REQUEST = -900008;//请求出错
    public static final int CODE_ERROR_NO_NET = -900009;//暂无网络
    public static final int CODE_ERROR_HTTP_TIMEOUT = -900007;//请求超时
    public static final int ERROR_DECODE = 100091;//返回结果解析出错
    public static final int ERROR_PARSE = 100092;//返回结果解析出错

    /**
     * 所有error的入口
     *
     * @param tag     从哪里来的表示 一般为Activity、Service等
     * @param context 上下文
     * @param code    错误码
     * @param msg     服务器返回的错误信息
     * @param isShow  是否显示Toast错误信息
     */
    //所有error的分发入口
    public static void executeError(String tag, Context context, int code, String msg, boolean isShow) {
        if (code == 0 && TextUtils.isEmpty(msg)) {
            msg = "网络不稳定";
        }
        executeBeforeShow(tag, context, code, msg);
        if (!isShow || context == null) {
            return;
        }
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            newOperateCode(context, code);
        }
    }

    /**
     * 在执行显示错误信息前 提前执行的操作
     *
     * @param tag
     * @param context
     * @param code
     * @param msg
     */
    //在执行显示错误信息前 提前执行的操作
    private static void executeBeforeShow(String tag, Context context, int code, String msg) {
//        if (CODE_USER_CHECK_FAILE == code) {//被强制退出
//            if (context != null) {
//                AccountManager.getInstance(context).logout();
//                ActivityUtil.startLoginActivity(context);
//                tokenTimeout = true;
//            }
//        }
    }

    /**
     * 当服务端返回的msg为空时，根据预定义的错误码，显示的错误信息
     *
     * @param context
     * @param code
     */
    //当服务端返回的msg为空时，根据预定义的错误码，显示的错误信息
    private static void newOperateCode(Context context, int code) {
        String msg;
        switch (code) {
            case CODE_ERROR_NO_NET:
                msg = context.getResources().getString(R.string.string_error_no_net);
                break;
            case CODE_ERROR_REQUEST:
                msg = context.getResources().getString(R.string.string_error_request);
                break;
            case CODE_ERROR_HTTP_TIMEOUT:
                msg = context.getResources().getString(R.string.string_error_http_timeout);
                break;
            case ERROR_DECODE:
                msg = context.getResources().getString(R.string.string_error_parse_exception);
                break;
            case ERROR_PARSE:
                msg = context.getResources().getString(R.string.string_error_parse_exception);
                break;
            default:
                msg = "" + code;
                break;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
