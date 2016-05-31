package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.transactionsResponse;

/**
 * Created by Administrator on 2016/5/13.
 */
public interface TransactionView extends BaseView{
    void refreshListData(transactionsResponse response);

    void addMoreListData(transactionsResponse response);
}
