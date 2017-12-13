package com.fy.catchdoll.presentation.model.dto.recharge;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by wst on 2017/12/2.
 */
public class RechargeItem extends BaseItemDto{
    private String id;
    private String name;
    private String price;
    private String number;
    private int give_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGive_number() {
        return give_number;
    }

    public void setGive_number(int give_number) {
        this.give_number = give_number;
    }

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
