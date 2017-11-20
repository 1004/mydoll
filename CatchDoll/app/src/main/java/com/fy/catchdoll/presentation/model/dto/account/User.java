package com.fy.catchdoll.presentation.model.dto.account;

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
    private String user_id;

    @DatabaseField
    private String user_type;

    @DatabaseField
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @DatabaseField
    private String nick;

    @DatabaseField
    private String avatar;

    @DatabaseField
    private String set_login_pwd;

    @DatabaseField
    private String user_verify;

    /**
     * 是否为最新的账号 1：是最新的 ，0:不是最新的
     */
    @DatabaseField
    private int islast;

    @DatabaseField
    private String gender;
    @DatabaseField
    private int fans;
    @DatabaseField
    private String level;
    @DatabaseField
    private int identity_auth;//是否以实名认证1为是0为否
    private Integer force_gps_address;//判断是否检查GPS权限
    private Integer try_author;//判断是否是试用
    private String[] title;
    public User() {

    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public Integer getTry_author() {
        return try_author;
    }

    public void setTry_author(Integer try_author) {
        this.try_author = try_author;
    }

    public Integer getForce_gps_address() {
        return force_gps_address;
    }

    public void setForce_gps_address(Integer force_gps_address) {
        this.force_gps_address = force_gps_address;
    }

    public String getUser_id() {
        return user_id;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getUser_type() {
        return user_type;
    }


    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }


    public String getNick() {
        return nick;
    }


    public void setNick(String nick) {
        this.nick = nick;
    }


    public String getAvatar() {
        return avatar;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getSet_login_pwd() {
        return set_login_pwd;
    }


    public void setSet_login_pwd(String set_login_pwd) {
        this.set_login_pwd = set_login_pwd;
    }


    public String getUser_verify() {
        return user_verify;
    }


    public void setUser_verify(String user_verify) {
        this.user_verify = user_verify;
    }


    public int getIslast() {
        return islast;
    }


    public void setIslast(int islast) {
        this.islast = islast;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getIdentity_auth() {
        return identity_auth;
    }

    public void setIdentity_auth(int identity_auth) {
        this.identity_auth = identity_auth;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_type='" + user_type + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nick='" + nick + '\'' +
                ", avatar='" + avatar + '\'' +
                ", set_login_pwd='" + set_login_pwd + '\'' +
                ", user_verify='" + user_verify + '\'' +
                ", islast=" + islast +
                ", gender='" + gender + '\'' +
                ", fans=" + fans +
                ", level='" + level + '\'' +
                ", identity_auth=" + identity_auth +
                ", force_gps_address=" + force_gps_address +
                ", try_author=" + try_author +
                ", title=" + Arrays.toString(title) +
                '}';
    }
}
