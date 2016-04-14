package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ChangePasswordInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ChangePasswordPresenter;
import com.dmd.zsb.mvp.view.ChangePasswordView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ChangePasswordPresenterImpl implements ChangePasswordPresenter,BaseSingleLoadedListener<JsonObject> {
    private ChangePasswordInteractorImpl changePasswordInteractor;
    private Context mContext;
    private ChangePasswordView changePasswordView;

    public ChangePasswordPresenterImpl(ChangePasswordView changePasswordView, Context mContext) {
        this.changePasswordView = changePasswordView;
        this.mContext = mContext;
        changePasswordInteractor=new ChangePasswordInteractorImpl(this);
    }

    @Override
    public void onChangePassword(JsonObject jsonObject) {
        changePasswordInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        changePasswordView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        changePasswordView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
