package com.qike.telecast.presentation.model.dto.doll;

import com.qike.telecast.presentation.model.dto.base.BaseItemDto;

import java.util.List;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class WrapDollMachineInfo extends BaseItemDto{
    private List<DollMachine> data;

    public List<DollMachine> getData() {
        return data;
    }

    public void setData(List<DollMachine> data) {
        this.data = data;
    }
}
