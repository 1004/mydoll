package com.fy.catchdoll.presentation.model.dto.doll.banner;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

import java.util.List;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class WrapBannerInfo extends BaseItemDto{
    private List<BannerInfo> banner;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }
}
