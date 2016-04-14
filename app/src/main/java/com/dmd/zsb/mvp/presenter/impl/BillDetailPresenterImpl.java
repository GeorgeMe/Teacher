package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.BillDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.BillDetailPresenter;
import com.dmd.zsb.mvp.view.BillDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class BillDetailPresenterImpl implements BillDetailPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private BillDetailView billDetailView;
    private BillDetailInteractorImpl billDetailInteractor;

    public BillDetailPresenterImpl(Context mContext, BillDetailView billDetailView) {
        this.mContext = mContext;
        this.billDetailView = billDetailView;
        billDetailInteractor=new BillDetailInteractorImpl(this);
    }

    @Override
    public void BillDetail(JsonObject jsonObject) {
        billDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        billDetailView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
