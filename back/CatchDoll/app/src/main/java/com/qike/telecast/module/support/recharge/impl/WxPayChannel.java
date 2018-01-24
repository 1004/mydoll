package com.qike.telecast.module.support.recharge.impl;

import android.content.Context;

import com.qike.telecast.module.support.recharge.base.IPayChannel;
import com.qike.telecast.module.support.recharge.common.dto.BasePayEntry;
import com.qike.telecast.module.support.recharge.common.dto.WxPayEntry;
import com.qike.umengshare_643.UmengInit;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by xky on 2017/12/11 0011.
 *
 * 微信支付渠道
 */
public class WxPayChannel implements IPayChannel{

    @Override
    public void Pay(Context context,BasePayEntry entry) {
        if (entry != null && entry instanceof WxPayEntry){
            IWXAPI wxapi = WXAPIFactory.createWXAPI(context,null);
            wxapi.registerApp(UmengInit.WEIXIN_APPID);

            WxPayEntry wxEntry = (WxPayEntry) entry;
            PayReq payRequest = new PayReq();
            payRequest.appId = wxEntry.getAppid();
            payRequest.partnerId = wxEntry.getPartnerid();
            payRequest.prepayId = wxEntry.getPrepayid();
            payRequest.packageValue = wxEntry.getPackage_field();
            payRequest.nonceStr = wxEntry.getNoncestr();
            payRequest.timeStamp = wxEntry.getTimestamp();
            payRequest.sign = wxEntry.getSign();
            //发送支付请求
            wxapi.sendReq(payRequest);
        }
    }

}
