package com.fy.catchdoll.presentation.model.dto.box;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxOrder {
    private String backpack_doll_total;
    private String price;
    private String packet_mail_number_text;

    public String getPacket_mail_number_text() {
        return packet_mail_number_text;
    }

    public void setPacket_mail_number_text(String packet_mail_number_text) {
        this.packet_mail_number_text = packet_mail_number_text;
    }

    public String getBackpack_doll_total() {
        return backpack_doll_total;
    }

    public void setBackpack_doll_total(String backpack_doll_total) {
        this.backpack_doll_total = backpack_doll_total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
