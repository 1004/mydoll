package com.fy.catchdoll.presentation.model.dto.box;

import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.doll.Doll;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxDoll extends BaseItemDto{
    private String id;
    private String user_id;
    private String doll_id;
    private String created_at;
    private Doll doll_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoll_id() {
        return doll_id;
    }

    public void setDoll_id(String doll_id) {
        this.doll_id = doll_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Doll getDoll_info() {
        return doll_info;
    }

    public void setDoll_info(Doll doll_info) {
        this.doll_info = doll_info;
    }
}
