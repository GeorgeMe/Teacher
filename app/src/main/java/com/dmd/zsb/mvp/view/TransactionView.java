package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.TransactionEntity;
import com.dmd.zsb.entity.response.TransactionResponse;

/**
 * Created by Administrator on 2016/4/6.
 */
public interface TransactionView extends BaseView {

    void navigateToTransactionDetail(TransactionEntity data);

    void refreshListData(TransactionResponse data);

    void addMoreListData(TransactionResponse data);
}
