package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.UpdateOrderStatusInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.UpdateOrderStatusPresenter;
import com.dmd.zsb.mvp.view.UpdateOrderStatusView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class UpdateOrderStatusPresenterImpl implements UpdateOrderStatusPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private UpdateOrderStatusView updateOrderStatusView;
    private UpdateOrderStatusInteractorImpl updateOrderStatusInteractor;

    public UpdateOrderStatusPresenterImpl(Context mContext, UpdateOrderStatusView updateOrderStatusView) {
        this.mContext = mContext;
        this.updateOrderStatusView = updateOrderStatusView;
        updateOrderStatusInteractor=new UpdateOrderStatusInteractorImpl(this);
    }

    @Override
    public void onUpdateOrderStatus(JsonObject jsonObject) {

    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        updateOrderStatusView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
