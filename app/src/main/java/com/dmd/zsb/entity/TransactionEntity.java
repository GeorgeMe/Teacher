package com.dmd.zsb.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/6.
 */
public class TransactionEntity implements Serializable {

    private String oid;//订单id
    private String img_header;//头像
    private String name;//姓名
    private String appointed_time;//约定时间
    private String charging;//计费
    private String curriculum;//课程
    private String address;//地址
    private String type;//地址
    private String note;//备注内容

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "img_header='" + img_header + '\'' +
                ", name='" + name + '\'' +
                ", appointed_time='" + appointed_time + '\'' +
                ", charging='" + charging + '\'' +
                ", curriculum='" + curriculum + '\'' +
                ", address='" + address + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
