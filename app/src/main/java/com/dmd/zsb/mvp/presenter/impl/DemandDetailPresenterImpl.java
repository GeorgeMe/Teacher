package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.DemandDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.DemandDetailPresenter;
import com.dmd.zsb.mvp.view.DemandDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DemandDetailPresenterImpl implements DemandDetailPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private DemandDetailView demandDetailView;
    private DemandDetailInteractorImpl demandDetailInteractor;

    public DemandDetailPresenterImpl(Context mContext, DemandDetailView demandDetailView) {
        this.mContext = mContext;
        this.demandDetailView = demandDetailView;
        demandDetailInteractor=new DemandDetailInteractorImpl(this);
    }

    @Override
    public void onDemandDetail(JsonObject jsonObject) {
        demandDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        demandDetailView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
