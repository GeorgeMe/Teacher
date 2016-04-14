package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.TransactionEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class TransactionResponse {
    private int total_page;
    private List<TransactionEntity> transactionEntities;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<TransactionEntity> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(List<TransactionEntity> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }
}
