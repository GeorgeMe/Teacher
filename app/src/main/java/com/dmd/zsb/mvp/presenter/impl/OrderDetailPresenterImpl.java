package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.OrderDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.OrderDetailPresenter;
import com.dmd.zsb.mvp.view.OrderDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class OrderDetailPresenterImpl implements OrderDetailPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private OrderDetailView orderDetailView;
    private OrderDetailInteractorImpl orderDetailInteractor;

    public OrderDetailPresenterImpl(Context mContext, OrderDetailView orderDetailView) {
        this.mContext = mContext;
        this.orderDetailView = orderDetailView;
        orderDetailInteractor=new OrderDetailInteractorImpl(this);
    }

    @Override
    public void onOrderDone(JsonObject jsonObject) {
        orderDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        orderDetailView.navigateToOrder();
    }

    @Override
    public void onError(String msg) {
        orderDetailView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
