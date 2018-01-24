package com.qike.telecast.presentation.model.dto.account;

import com.j256.ormlite.field.DatabaseField;

import java.util.Arrays;

/**
 * <p>用户实体类</p><br/>
 *
 * @author sll
 * @since 1.0.0
 */
public class User {
    public static String MANGENDER = "man";
    public static String WOMANGENDER = "woman";
    public static int IDENTITY_AUTH_YES = 1;
    public static int IDENTITY_AUTH_NO = 0;

    /**
     * 主播用户
     */
    public static final int ANCHOR_USER = 1;
    /**
     * 普通用户
     */
    public static final int COMMON_USER = 0;
    /**
     * 用户id
     */
    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String nickname;

    @DatabaseField
    private String headimgurl;

    /**
     * 是否为最新的账号 1：是最新的 ，0:不是最新的
     */
    @DatabaseField
    private int islast;

    @DatabaseField
    private String sex;
    @DatabaseField
    private String access_token;
    @DatabaseField
    private String invitation_code;
    @DatabaseField
    private String invitation_total;
    @DatabaseField
    private String invitation_gold;
    @DatabaseField
    private String max_invitation_friends;
    @DatabaseField
    private int gold;

    public User() {

    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIslast() {
        return islast;
    }

    public void setIslast(int islast) {
        this.islast = islast;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getInvitation_total() {
        return invitation_total;
    }

    public void setInvitation_total(String invitation_total) {
        this.invitation_total = invitation_total;
    }

    public String getInvitation_gold() {
        return invitation_gold;
    }

    public void setInvitation_gold(String invitation_gold) {
        this.invitation_gold = invitation_gold;
    }

    public String getMax_invitation_friends() {
        return max_invitation_friends;
    }

    public void setMax_invitation_friends(String max_invitation_friends) {
        this.max_invitation_friends = max_invitation_friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", islast=" + islast +
                ", sex='" + sex + '\'' +
                ", access_token='" + access_token + '\'' +
                ", invitation_code='" + invitation_code + '\'' +
                ", invitation_total='" + invitation_total + '\'' +
                ", invitation_gold='" + invitation_gold + '\'' +
                ", max_invitation_friends='" + max_invitation_friends + '\'' +
                '}';
    }
}
