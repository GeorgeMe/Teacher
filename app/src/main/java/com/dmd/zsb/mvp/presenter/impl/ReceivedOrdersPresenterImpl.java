package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ReceivedOrdersInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.ReceivedOrdersPresenter;
import com.dmd.zsb.mvp.view.ReceivedOrdersView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ReceivedOrdersPresenterImpl implements ReceivedOrdersPresenter,BaseMultiLoadedListener<JsonObject> {
    private Context mContext;
    private ReceivedOrdersView receivedOrdersView;
    private ReceivedOrdersInteractorImpl receivedOrdersInteractor;

    public ReceivedOrdersPresenterImpl(Context mContext, ReceivedOrdersView receivedOrdersView) {
        this.mContext = mContext;
        this.receivedOrdersView = receivedOrdersView;
        receivedOrdersInteractor=new ReceivedOrdersInteractorImpl(this);
    }

    @Override
    public void onReceivedOrders(int event,JsonObject jsonObject) {
        receivedOrdersInteractor.getCommonListData(event,jsonObject);
    }

    @Override
    public void onSuccess(int event_tag, JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        receivedOrdersView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
