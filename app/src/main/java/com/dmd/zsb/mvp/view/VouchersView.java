package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.VouchersEntity;
import com.dmd.zsb.entity.response.VouchersResponse;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface VouchersView extends BaseView{

    void navigateToVouchersDetail(int position, VouchersEntity itemData);

    void refreshListData(VouchersResponse data);

    void addMoreListData(VouchersResponse data);
}
