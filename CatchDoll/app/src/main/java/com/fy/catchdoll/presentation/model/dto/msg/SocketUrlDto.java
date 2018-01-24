package com.fy.catchdoll.presentation.model.dto.msg;

/**
 * Created by xky on 16/6/21.
 */
public class SocketUrlDto {
    private String wsurl;
    private String room_id_prefix;
    private String user_id_prefix;

    public String getRoom_id_prefix() {
        return room_id_prefix;
    }

    public void setRoom_id_prefix(String room_id_prefix) {
        this.room_id_prefix = room_id_prefix;
    }

    public String getUser_id_prefix() {
        return user_id_prefix;
    }

    public void setUser_id_prefix(String user_id_prefix) {
        this.user_id_prefix = user_id_prefix;
    }

    public String getWsurl() {
        return wsurl;
    }

    public void setWsurl(String wsurl) {
        this.wsurl = wsurl;
    }
}
