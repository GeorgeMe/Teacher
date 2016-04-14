package com.dmd.zsb.entity;

/**
 * Created by Administrator on 2016/3/29.
 * 代金卷
 */
public class VouchersEntity {

    private String img_path;//图片地址
    private String not;//说明
    private String validity_period;//有效期
    private String state;

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public String getValidity_period() {
        return validity_period;
    }

    public void setValidity_period(String validity_period) {
        this.validity_period = validity_period;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
