package com.fy.catchdoll.presentation.model.dto.room;

import com.fy.catchdoll.presentation.model.dto.account.User;

import java.util.List;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class EnterRoomDto {
    private User user;
    private RoomInfo machine;
    private List<CatchRecord> grab_record;

    public User getUser() {
        return user;
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
