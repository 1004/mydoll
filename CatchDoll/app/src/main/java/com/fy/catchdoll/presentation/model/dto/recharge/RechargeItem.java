package com.fy.catchdoll.presentation.model.dto.recharge;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by wst on 2017/12/2.
 */
public class RechargeItem extends BaseItemDto{
    private String name;
    private String price;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
