package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.VouchersDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.VouchersDetailPresenter;
import com.dmd.zsb.mvp.view.VouchersDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class VouchersDetailPresenterImpl implements VouchersDetailPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private VouchersDetailView vouchersDetailView;
    private VouchersDetailInteractorImpl vouchersDetailInteractor;

    public VouchersDetailPresenterImpl(Context mContext, VouchersDetailView vouchersDetailView) {
        this.mContext = mContext;
        this.vouchersDetailView = vouchersDetailView;
        vouchersDetailInteractor=new VouchersDetailInteractorImpl(this);
    }

    @Override
    public void onVouchersDetail(JsonObject jsonObject) {
        vouchersDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        vouchersDetailView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
