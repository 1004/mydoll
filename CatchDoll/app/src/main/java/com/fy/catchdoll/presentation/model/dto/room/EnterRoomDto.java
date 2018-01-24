package com.fy.catchdoll.presentation.model.dto.room;

import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.msg.SocketUrlDto;

import java.util.List;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class EnterRoomDto {
    private User user;
    private RoomInfo machine;
    private List<CatchRecord> grab_record;
    private SocketUrlDto danmu;
    public User getUser() {
        return user;
    }


    public SocketUrlDto getDanmu() {
        return danmu;
    }

    public void setDanmu(SocketUrlDto danmu) {
        this.danmu = danmu;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoomInfo getMachine() {
        return machine;
    }

    public void setMachine(RoomInfo machine) {
        this.machine = machine;
    }

    public List<CatchRecord> getGrab_record() {
        return grab_record;
    }

    public void setGrab_record(List<CatchRecord> grab_record) {
        this.grab_record = grab_record;
    }
}
