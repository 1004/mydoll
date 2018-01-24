package com.fy.catchdoll.presentation.model.dto.msg;

/**
 * Created by xky on 16/8/11.
 * 发送消息
 */
public class SentMessDto {
    private int type;
    private String is_mobile;
    private String user_id;// 发送消息人uid
    private String user_nick; // 发送消息人的昵称
    private String user_avatar;// 发送消息人的头像地址，普通消息无头像
    private String sign;
    private String content;// 消息内容

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIs_mobile() {
        return is_mobile;
    }

    public void setIs_mobile(String is_mobile) {
        this.is_mobile = is_mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
