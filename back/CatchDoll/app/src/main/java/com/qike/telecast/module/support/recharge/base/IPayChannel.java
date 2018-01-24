package com.qike.telecast.module.support.recharge.base;

import android.content.Context;

import com.qike.telecast.module.support.recharge.common.dto.BasePayEntry;

/**
 * Created by xky on 2017/12/11 0011.
 *
 * 支付渠道
 */
public interface IPayChannel {
    void Pay(Context context,BasePayEntry entry);
}
