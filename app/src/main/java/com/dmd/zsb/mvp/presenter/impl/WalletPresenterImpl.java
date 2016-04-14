package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.WalletInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.WalletPresenter;
import com.dmd.zsb.mvp.view.WalletView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class WalletPresenterImpl implements WalletPresenter,BaseSingleLoadedListener<JsonObject> {
    private WalletView walletView;
    private Context mContext;
    private WalletInteractorImpl walletInteractor;

    public WalletPresenterImpl(WalletView walletView, Context mContext) {
        this.walletView = walletView;
        this.mContext = mContext;
        walletInteractor=new WalletInteractorImpl(this);
    }

    @Override
    public void onWalletInfo(JsonObject jsonObject) {
        walletInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        walletView.setView(data);
    }

    @Override
    public void onError(String msg) {
        walletView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
