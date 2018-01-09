package com.fy.catchdoll.presentation.model.dto.room;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class CatchRecord {
    private String nickname;
    private String headimgurl;
    private String grab_time;
    private String video_link;

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

    public String getGrab_time() {
        return grab_time;
    }

    public void setGrab_time(String grab_time) {
        this.grab_time = grab_time;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }
}
