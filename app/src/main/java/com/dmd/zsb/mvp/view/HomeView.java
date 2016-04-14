package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.entity.response.HomeResponse;

/**
 * Created by George on 2015/12/6.
 */
public interface HomeView extends BaseView{

    void navigateToOrderDetail(OrderEntity data);

    void refreshListData(HomeResponse data);

    void addMoreListData(HomeResponse data);

}
