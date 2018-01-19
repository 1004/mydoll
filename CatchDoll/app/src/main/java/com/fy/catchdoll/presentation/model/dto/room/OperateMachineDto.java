package com.fy.catchdoll.presentation.model.dto.room;

/**
 * Created by xky on 2018/1/10 0010.
 */
public class OperateMachineDto {
    public static final String START = "start";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String GET = "get";
    public static final String UP = "up";

    private String type;
    private int gold;
    private String record_id;

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
