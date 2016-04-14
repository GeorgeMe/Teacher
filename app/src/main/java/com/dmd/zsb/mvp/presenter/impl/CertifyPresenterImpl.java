package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.CertifyInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.CertifyPresenter;
import com.dmd.zsb.mvp.view.CertifyView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CertifyPresenterImpl implements CertifyPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private CertifyView certifyView;
    private CertifyInteractorImpl certifyInteractor;

    public CertifyPresenterImpl(Context mContext, CertifyView certifyView) {
        this.mContext = mContext;
        this.certifyView = certifyView;
        certifyInteractor=new CertifyInteractorImpl(this);
    }

    @Override
    public void onCertify(JsonObject jsonObject) {
        certifyInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        certifyView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
