package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.entity.response.OrderResponse;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface OrderView extends BaseView{

    void navigateToOrderDetail(OrderEntity data);

    void refreshListData(OrderResponse data);

    void addMoreListData(OrderResponse data);
}
