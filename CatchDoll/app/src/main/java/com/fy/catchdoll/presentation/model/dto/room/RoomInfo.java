package com.fy.catchdoll.presentation.model.dto.room;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class RoomInfo {
    public static int STATE_GAMEING = 1;
    public static int STATE_GAME_FREE= 0;
    private String id;
    private String doll_id;
    private String gold;
    private int game_state;//游戏状态，1:游戏中   0:空闲中
    private String channel_id;

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoll_id() {
        return doll_id;
    }

    public void setDoll_id(String doll_id) {
        this.doll_id = doll_id;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public int getGame_state() {
        return game_state;
    }

    public void setGame_state(int game_state) {
        this.game_state = game_state;
    }
}
