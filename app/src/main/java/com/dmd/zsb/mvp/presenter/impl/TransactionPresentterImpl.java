package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.response.TransactionResponse;
import com.dmd.zsb.mvp.interactor.impl.TransactionInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.TransactionPresentter;
import com.dmd.zsb.mvp.view.TransactionView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/6.
 */
public class TransactionPresentterImpl implements TransactionPresentter,BaseMultiLoadedListener<TransactionResponse> {
    private Context mContext;
    private TransactionView transactionView;
    private TransactionInteractorImpl transactionInteractor;

    public TransactionPresentterImpl(Context mContext, TransactionView transactionView) {
        this.mContext = mContext;
        this.transactionView = transactionView;
        transactionInteractor=new TransactionInteractorImpl(this);
    }

    @Override
    public void onTransaction(int event_tag, JsonObject jsonObject) {
        transactionInteractor.getCommonListData(event_tag,jsonObject);
    }

    @Override
    public void onSuccess(int event_tag, TransactionResponse data) {
        if (event_tag== Constants.EVENT_REFRESH_DATA){
            transactionView.refreshListData(data);
        }else if (event_tag==Constants.EVENT_LOAD_MORE_DATA){
            transactionView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        transactionView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
