package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.AcceptOrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.AcceptOrderPresenter;
import com.dmd.zsb.mvp.view.AcceptOrderView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class AcceptOrderPresenterImpl implements AcceptOrderPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private AcceptOrderView acceptOrderView;
    private AcceptOrderInteractorImpl acceptOrderInteractor;

    public AcceptOrderPresenterImpl(Context mContext, AcceptOrderView acceptOrderView) {
        this.mContext = mContext;
        this.acceptOrderView = acceptOrderView;
        acceptOrderInteractor=new AcceptOrderInteractorImpl(this);
    }

    @Override
    public void onAcceptOrder(JsonObject jsonObject) {
        acceptOrderInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        acceptOrderView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
