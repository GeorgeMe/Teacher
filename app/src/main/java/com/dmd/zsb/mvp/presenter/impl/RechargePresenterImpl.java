package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.RechargeInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.RechargePresenter;
import com.dmd.zsb.mvp.view.RechargeView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class RechargePresenterImpl implements RechargePresenter ,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private RechargeView rechargeView;
    private RechargeInteractorImpl rechargeInteractor;

    public RechargePresenterImpl(Context mContext, RechargeView rechargeView) {
        this.mContext = mContext;
        this.rechargeView = rechargeView;
        rechargeInteractor=new RechargeInteractorImpl(this);
    }

    @Override
    public void onRecharge(JsonObject jsonObject) {
        rechargeInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        rechargeView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
