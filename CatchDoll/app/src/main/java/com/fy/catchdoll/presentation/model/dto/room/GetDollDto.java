package com.fy.catchdoll.presentation.model.dto.room;

import com.fy.catchdoll.presentation.model.dto.doll.Doll;

/**
 * Created by xky on 2018/1/11 0011.
 */
public class GetDollDto {
    public static final int GRAB_OK = 1;
    public static final int GRAB_NO = 0;

    private int is_grab;//是否抓中  1:是  0:否
    private Doll doll_info;

    public Doll getDoll_info() {
        return doll_info;
    }

    public void setDoll_info(Doll doll_info) {
        this.doll_info = doll_info;
    }

    public int getIs_grab() {
        return is_grab;
    }

    public void setIs_grab(int is_grab) {
        this.is_grab = is_grab;
    }
}
