package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.TransactionDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.TransactionDetailPresenter;
import com.dmd.zsb.mvp.view.TransactionDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TransactionDetailPresenterImpl implements TransactionDetailPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private TransactionDetailView transactionDetailView;
    private TransactionDetailInteractorImpl transactionDetailInteractor;

    public TransactionDetailPresenterImpl(Context mContext, TransactionDetailView transactionDetailView) {
        this.mContext = mContext;
        this.transactionDetailView = transactionDetailView;
        transactionDetailInteractor=new TransactionDetailInteractorImpl(this);
    }

    @Override
    public void onAcceptOrder(JsonObject jsonObject) {
        transactionDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        transactionDetailView.navigateToTransaction();
    }

    @Override
    public void onError(String msg) {
        transactionDetailView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
