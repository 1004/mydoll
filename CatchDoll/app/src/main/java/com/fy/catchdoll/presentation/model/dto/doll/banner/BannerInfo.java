package com.fy.catchdoll.presentation.model.dto.doll.banner;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BannerInfo extends BaseItemDto{
    private String title;
    private String image;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}