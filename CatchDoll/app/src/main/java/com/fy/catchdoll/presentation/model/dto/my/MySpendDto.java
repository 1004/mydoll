package com.fy.catchdoll.presentation.model.dto.my;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by xky on 2017/11/30 0030.
 */
public class MySpendDto extends BaseItemDto{
    private String id;
    private String user_id;
    private String number;
    private String type;
    private String remarks;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
