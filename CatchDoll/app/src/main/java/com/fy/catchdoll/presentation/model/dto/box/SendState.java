package com.fy.catchdoll.presentation.model.dto.box;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by xky on 2017/12/14 0014.
 * 发货状态
 */
public class SendState extends BaseItemDto{
    private String order_no;
    private String created_at;
    private String status;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
