package com.fy.catchdoll.presentation.model.dto.doll;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class DollMachine {
    public static final int FREE = 1;//空闲
    public static final int BUSY = 0;//游戏中
    private String id;///娃娃机ID
    private String doll_id;
    private String gold;
    private String sort;
    private int status;
    private String created_at;
    private String updated_at;
    private String doll_title;
    private String doll_image;
    private int is_game;

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDoll_title() {
        return doll_title;
    }

    public void setDoll_title(String doll_title) {
        this.doll_title = doll_title;
    }

    public String getDoll_image() {
        return doll_image;
    }

    public void setDoll_image(String doll_image) {
        this.doll_image = doll_image;
    }

    public int getIs_game() {
        return is_game;
    }

    public void setIs_game(int is_game) {
        this.is_game = is_game;
    }
}
