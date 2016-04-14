package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.OrderInfoInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.OrderInfoPresenter;
import com.dmd.zsb.mvp.view.OrderInfoView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class OrderInfoPresenterImpl implements OrderInfoPresenter ,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private OrderInfoView orderInfoView;
    private OrderInfoInteractorImpl orderInfoInteractor;

    public OrderInfoPresenterImpl(Context mContext, OrderInfoView orderInfoView) {
        this.mContext = mContext;
        this.orderInfoView = orderInfoView;
        orderInfoInteractor=new OrderInfoInteractorImpl(this);
    }

    @Override
    public void onOrderInfo(JsonObject jsonObject) {
        orderInfoInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        orderInfoView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
