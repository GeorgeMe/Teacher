package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.BankcardInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.BankcardPresenter;
import com.dmd.zsb.mvp.view.BankcardView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class BankcardPresenterImpl implements BankcardPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private BankcardView bankcardView;
    private BankcardInteractorImpl bankcardInteractor;

    public BankcardPresenterImpl(Context mContext, BankcardView bankcardView) {
        this.mContext = mContext;
        this.bankcardView = bankcardView;
        bankcardInteractor=new BankcardInteractorImpl(this);
    }

    @Override
    public void onBankcard(JsonObject jsonObject) {
        bankcardInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        bankcardView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
