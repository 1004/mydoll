package com.fy.catchdoll.presentation.model.dto.doll.banner;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BannerInfo extends BaseItemDto{
    public static final int BANNER_WAP = 1;
    public static final int BANNER_PAY = 2;
    public static final int BANNER_ROOM = 3;
    public static final int BANNER_SHARE = 4;
    private String title;
    private String image;
    private String link;
    private String machine_id;
    private int jump_type;

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

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
