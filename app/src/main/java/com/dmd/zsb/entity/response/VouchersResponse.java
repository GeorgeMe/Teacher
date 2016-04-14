package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.VouchersEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class VouchersResponse {
    private int total_page;
    private List<VouchersEntity> vouchersEntities;

    public List<VouchersEntity> getVouchersEntities() {
        return vouchersEntities;
    }

    public void setVouchersEntities(List<VouchersEntity> vouchersEntities) {
        this.vouchersEntities = vouchersEntities;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
}
