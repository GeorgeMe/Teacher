package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.WalletInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.WalletPresenter;
import com.dmd.zsb.mvp.view.WalletView;
import com.dmd.zsb.protocol.request.walletRequest;
import com.dmd.zsb.protocol.response.walletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class WalletPresenterImpl implements WalletPresenter,BaseSingleLoadedListener<walletResponse> {
    private WalletView walletView;
    private Context mContext;
    private WalletInteractorImpl walletInteractor;

    public WalletPresenterImpl(WalletView walletView, Context mContext) {
        this.walletView = walletView;
        this.mContext = mContext;
        walletInteractor=new WalletInteractorImpl(this);
    }

    @Override
    public void onWalletInfo(JSONObject jsonObject) {
        walletView.showLoading(null);
        walletRequest request=new walletRequest();
        try {
            request.fromJson(jsonObject);
            walletInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(walletResponse response) {
        walletView.hideLoading();
        walletView.setView(response);
    }

    @Override
    public void onError(String msg) {
        walletView.hideLoading();
        walletView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
