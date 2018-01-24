package com.qike.telecast.wxapi;

import android.os.Bundle;

import com.umeng.socialize.weixin.view.WXCallbackActivity;


public class WXEntryActivity extends WXCallbackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            finish();
        }
    }

    /**
     * 6.0.1
     * 对于的低端手机可能会有如下问题，从开发者app调到qq或者微信的授权界面，后台把开发者app杀死了，
     * 这样，授权成功没有回调了，可以用如下方式避免：（一般不需要添加，如有特殊需要，可以添加）
     *
     * @param intent
     */
//    @Override
//    protected void handleIntent(Intent intent) {
////        if (mWxHandler != null) {
////            mWxHandler.setAuthListener(new UMAuthListener() {
////                @Override
////                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
////                    PushLogUtils.v("WXEntryActivity", "handleIntent----------onComplete");
////                }
////
////                @Override
////                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
////                    PushLogUtils.v("WXEntryActivity", "handleIntent----------onError ");
////                }
////
////                @Override
////                public void onCancel(SHARE_MEDIA share_media, int i) {
////                    PushLogUtils.v("WXEntryActivity", "handleIntent----------onCancel ");
////                }
////            });
////        }
////        super.handleIntent(intent);
//    }
}
