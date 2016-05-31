package com.dmd.pay.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/12.
 */
public class PayInfo implements Serializable {

    private String name;//商品名称
    private String desc;//详细描述
    private Double price;//价格
    private Double rate;//折扣
    private String order_sn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
