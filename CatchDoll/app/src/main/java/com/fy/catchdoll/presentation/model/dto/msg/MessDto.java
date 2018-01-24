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
    private String user_id;// 发送消息人uid
    private String nickname;
    private String headimgurl;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
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
