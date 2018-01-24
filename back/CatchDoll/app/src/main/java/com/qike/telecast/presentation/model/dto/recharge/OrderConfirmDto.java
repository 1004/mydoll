package com.qike.telecast.presentation.model.dto.recharge;

/**
 * Created by xky on 2017/12/13 0013.
 */
public class OrderConfirmDto {
    private String order_no;
    private String price;
    private int gold_number;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getGold_number() {
        return gold_number;
    }

    public void setGold_number(int gold_number) {
        this.gold_number = gold_number;
    }
}
