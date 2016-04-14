package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.OrderEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class OrderResponse {
    private int total_page;
    private List<OrderEntity> orderEntities;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}
