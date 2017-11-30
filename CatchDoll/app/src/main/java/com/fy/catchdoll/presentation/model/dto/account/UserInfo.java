package com.fy.catchdoll.presentation.model.dto.account;

import java.io.Serializable;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class UserInfo implements Serializable {
    private String id;
    private String username;
    private String head_pic;
    private String wx_openid;
    private String gold;
    private String invitation_code;
    private String invitation_total;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getInvitation_total() {
        return invitation_total;
    }

    public void setInvitation_total(String invitation_total) {
        this.invitation_total = invitation_total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
