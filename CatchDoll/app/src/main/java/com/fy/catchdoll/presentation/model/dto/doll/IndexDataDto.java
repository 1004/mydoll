package com.fy.catchdoll.presentation.model.dto.doll;

import com.fy.catchdoll.presentation.model.dto.doll.banner.BannerInfo;

import java.util.List;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class IndexDataDto {
    private List<BannerInfo> banner;
    private List<DollMachine> machine_list;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<DollMachine> getMachine_list() {
        return machine_list;
    }

    public void setMachine_list(List<DollMachine> machine_list) {
        this.machine_list = machine_list;
    }
}
