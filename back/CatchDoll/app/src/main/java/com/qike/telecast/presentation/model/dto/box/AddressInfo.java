package com.qike.telecast.presentation.model.dto.box;

import com.qike.telecast.presentation.model.dto.base.BaseItemDto;

import java.io.Serializable;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class AddressInfo extends BaseItemDto implements Serializable{
    private String id;
    private String user_id;
    private String name;
    private String phone;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
