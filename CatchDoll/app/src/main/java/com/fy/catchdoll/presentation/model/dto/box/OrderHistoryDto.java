package com.fy.catchdoll.presentation.model.dto.box;

import java.util.List;

/**
 * Created by xky on 2017/12/14 0014.
 */
public class OrderHistoryDto {
    private SendState order;
    private AddressInfo address;
    private List<BoxDoll> doll_list;

    public SendState getOrder() {
        return order;
    }

    public void setOrder(SendState order) {
        this.order = order;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public List<BoxDoll> getDoll_list() {
        return doll_list;
    }

    public void setDoll_list(List<BoxDoll> doll_list) {
        this.doll_list = doll_list;
    }

}
