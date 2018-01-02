package com.fy.catchdoll.presentation.model.dto.recharge;

/**
 * Created by xky on 2017/12/13 0013.
 */
public class OrderExpress {
    private int is_free_shipping;////是否包邮    1:包邮   0:需要支付邮费
    private String success_msg;

    public int getIs_free_shipping() {
        return is_free_shipping;
    }

    public void setIs_free_shipping(int is_free_shipping) {
        this.is_free_shipping = is_free_shipping;
    }

    public String getSuccess_msg() {
        return success_msg;
    }

    public void setSuccess_msg(String success_msg) {
        this.success_msg = success_msg;
    }
}
