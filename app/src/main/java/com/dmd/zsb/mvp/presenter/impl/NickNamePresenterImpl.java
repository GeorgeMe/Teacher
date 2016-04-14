package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.NickNameInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.NickNamePresenter;
import com.dmd.zsb.mvp.view.NickNameView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class NickNamePresenterImpl implements NickNamePresenter,BaseSingleLoadedListener<JsonObject> {
    private NickNameInteractorImpl nickNameInteractor;
    private Context mContext;
    private NickNameView nickNameView;

    public NickNamePresenterImpl(NickNameView nickNameView, Context mContext) {
        this.nickNameView = nickNameView;
        this.mContext = mContext;
        nickNameInteractor=new NickNameInteractorImpl(this);
    }

    @Override
    public void updateNickName(JsonObject jsonObject) {
        nickNameInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        nickNameView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        nickNameView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
