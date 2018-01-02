package com.fy.catchdoll.presentation.model.dto.recharge;

import com.fy.catchdoll.module.support.recharge.common.dto.WxPayEntry;

/**
 * Created by xky on 2017/12/13 0013.
 */
public class BoxOrderDto {
    private OrderExpress express;
    private WxPayEntry wx_pay;

    public OrderExpress getExpress() {
        return express;
    }

    public void setExpress(OrderExpress express) {
        this.express = express;
    }

    public WxPayEntry getWx_pay() {
        return wx_pay;
    }

    public void setWx_pay(WxPayEntry wx_pay) {
        this.wx_pay = wx_pay;
    }
}
