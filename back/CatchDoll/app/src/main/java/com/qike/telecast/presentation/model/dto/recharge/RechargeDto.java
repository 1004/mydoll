package com.qike.telecast.presentation.model.dto.recharge;

import com.qike.telecast.presentation.model.dto.doll.banner.BannerInfo;

import java.util.List;

/**
 * Created by xky on 2017/12/11 0011.
 */
public class RechargeDto {
    private List<BannerInfo> banner;
    private List<RechargeItem> package_list;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<RechargeItem> getPackage_list() {
        return package_list;
    }

    public void setPackage_list(List<RechargeItem> package_list) {
        this.package_list = package_list;
    }
}
