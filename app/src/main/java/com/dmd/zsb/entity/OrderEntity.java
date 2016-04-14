package com.dmd.zsb.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/29.
 * 我的订单
 */
public class OrderEntity implements Serializable {

    private String oid;//订单id
    private String img_header;//头像
    private String name;//姓名
    private String type;//职业类型
    private String sex;//性别
    private String appointed_time;//约定时间
    private String charging;//计费
    private String curriculum;//课程
    private String address;//地址
    private String place;//地点
    private String state;//状态
    private String praise;//好评

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getImg_header() {
        return img_header;
    }

    public void setImg_header(String img_header) {
        this.img_header = img_header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAppointed_time() {
        return appointed_time;
    }

    public void setAppointed_time(String appointed_time) {
        this.appointed_time = appointed_time;
    }

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
