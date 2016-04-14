package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.CancelOrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.CancelOrderPresenter;
import com.dmd.zsb.mvp.view.CancelOrderView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CancelOrderPresenterImpl implements CancelOrderPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private CancelOrderView cancelOrderView;
    private CancelOrderInteractorImpl cancelOrderInteractor;

    public CancelOrderPresenterImpl(Context mContext, CancelOrderView cancelOrderView) {
        this.mContext = mContext;
        this.cancelOrderView = cancelOrderView;
        cancelOrderInteractor=new CancelOrderInteractorImpl(this);
    }

    @Override
    public void onCancelOrder(JsonObject jsonObject) {
        cancelOrderInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        cancelOrderView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
