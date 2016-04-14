package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.WithdrawalsInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.WithdrawalsPresenter;
import com.dmd.zsb.mvp.view.WithdrawalsView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class WithdrawalsPresenterImpl implements WithdrawalsPresenter ,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private WithdrawalsView withdrawalsView;
    private WithdrawalsInteractorImpl withdrawalsInteractor;

    public WithdrawalsPresenterImpl(Context mContext, WithdrawalsView withdrawalsView) {
        this.mContext = mContext;
        this.withdrawalsView = withdrawalsView;
        withdrawalsInteractor=new WithdrawalsInteractorImpl(this);
    }

    @Override
    public void onWithdrawals(JsonObject jsonObject) {
        withdrawalsInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        withdrawalsView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
