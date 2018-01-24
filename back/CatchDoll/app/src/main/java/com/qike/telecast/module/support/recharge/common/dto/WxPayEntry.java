package com.qike.telecast.module.support.recharge.common.dto;

/**
 * Created by xky on 2017/12/11 0011.
 * 微信支付 实体
 */
public class WxPayEntry extends BasePayEntry{
    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String sign;
    private String package_field;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackage_field() {
        return package_field;
    }

    public void setPackage_field(String package_field) {
        this.package_field = package_field;
    }
}
