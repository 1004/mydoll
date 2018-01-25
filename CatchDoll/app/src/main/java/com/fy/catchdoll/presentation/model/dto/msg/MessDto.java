package com.fy.catchdoll.presentation.model.dto.msg;


import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

import java.util.ArrayList;
import java.util.Arrays;

public class MessDto extends BaseItemDto implements Cloneable {

    public static final int NORMAL = 1;// 普通

    /**
     * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓自己设置，网络获取不到
     */
    public static final int SOCKET_STATUS = 102;// Socket状态
    // :正在连接弹幕服务器:弹幕服务器链接成功:弹幕服务器连接失败
    /**
     * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
     */

    // 消息种类： 1.普通文字弹幕
    private int type;
    private int waka_type;
    private String is_mobile;
    private String user_id;// 发送消息人uid
    private String user_nick; // 发送消息人的昵称
    private String user_avatar;// 发送消息人的头像地址，普通消息无头像
    private String content;// 消息内容


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWaka_type() {
        return waka_type;
    }

    public void setWaka_type(int waka_type) {
        this.waka_type = waka_type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Object clone() {
        MessDto o = null;
        try {
            o = (MessDto) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

}
