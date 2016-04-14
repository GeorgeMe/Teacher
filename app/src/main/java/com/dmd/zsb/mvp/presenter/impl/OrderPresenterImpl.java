package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.response.OrderResponse;
import com.dmd.zsb.mvp.interactor.impl.OrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.OrderPresenter;
import com.dmd.zsb.mvp.view.OrderView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class OrderPresenterImpl implements OrderPresenter ,BaseMultiLoadedListener<OrderResponse>{
    private Context mContext;
    private OrderInteractorImpl orderInteractor;
    private OrderView orderView;

    public OrderPresenterImpl(Context mContext, OrderView orderView) {
        this.mContext = mContext;
        this.orderView = orderView;
        orderInteractor=new OrderInteractorImpl(this);
    }

    @Override
    public void onOrder(int event_tag, JsonObject jsonObject) {
        orderInteractor.getCommonListData(event_tag,jsonObject);
    }

    @Override
    public void onSuccess(int event_tag, OrderResponse data) {
        if (event_tag== Constants.EVENT_LOAD_MORE_DATA){
            orderView.addMoreListData(data);
        }else if (event_tag==Constants.EVENT_REFRESH_DATA){
            orderView.refreshListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        orderView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        orderView.showException(msg);
    }
}
