package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.SignatureInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignaturePresenter;
import com.dmd.zsb.mvp.view.SignatureView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SignaturePresenterImpl implements SignaturePresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private SignatureView signatureView;
    private SignatureInteractorImpl signatureInteractor;

    public SignaturePresenterImpl(Context mContext, SignatureView signatureView) {
        this.mContext = mContext;
        this.signatureView = signatureView;
        signatureInteractor=new SignatureInteractorImpl(this);
    }

    @Override
    public void onSignature(JsonObject jsonObject) {
        signatureInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        signatureView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
