package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.orderResponse;
import com.dmd.zsb.protocol.table.OrdersBean;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface OrderView extends BaseView{

    void navigateToOrderDetail(OrdersBean data);

    void refreshListData(orderResponse response);

    void addMoreListData(orderResponse response);
}
