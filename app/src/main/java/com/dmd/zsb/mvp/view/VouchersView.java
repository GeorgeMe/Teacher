package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.vouchersResponse;
import com.dmd.zsb.protocol.table.VouchersBean;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface VouchersView extends BaseView{

    void navigateToVouchersDetail(int position, VouchersBean itemData);

    void refreshListData(vouchersResponse response);

    void addMoreListData(vouchersResponse response);
}
