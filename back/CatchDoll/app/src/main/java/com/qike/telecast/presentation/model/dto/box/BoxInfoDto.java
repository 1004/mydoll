package com.qike.telecast.presentation.model.dto.box;

import java.util.List;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxInfoDto {
    private List<BoxDoll> backpack_doll_list;
    private AddressInfo address;
    private BoxOrder express;

    public List<BoxDoll> getBackpack_doll_list() {
        return backpack_doll_list;
    }

    public void setBackpack_doll_list(List<BoxDoll> backpack_doll_list) {
        this.backpack_doll_list = backpack_doll_list;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public BoxOrder getExpress() {
        return express;
    }

    public void setExpress(BoxOrder express) {
        this.express = express;
    }
}
