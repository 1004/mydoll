package com.fy.catchdoll.presentation.model.dto.doll;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

/**
 * Created by xky on 2018/1/29 0029.
 */
public class CatchHistoryDto extends BaseItemDto{
    private String id;//游戏编号
    private int is_grab;//是否抓取成功  1:成功  0:失败
    private int is_appeal;//是否已申诉   1:已申诉   0:未申诉
    private String created_at;
    private Doll doll_info;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIs_grab() {
        return is_grab;
    }

    public void setIs_grab(int is_grab) {
        this.is_grab = is_grab;
    }

    public int getIs_appeal() {
        return is_appeal;
    }

    public void setIs_appeal(int is_appeal) {
        this.is_appeal = is_appeal;
    }

    public Doll getDoll_info() {
        return doll_info;
    }

    public void setDoll_info(Doll doll_info) {
        this.doll_info = doll_info;
    }
}
