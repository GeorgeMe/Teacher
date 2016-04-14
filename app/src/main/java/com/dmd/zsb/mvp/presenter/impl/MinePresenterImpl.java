package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.MineInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.MinePresenter;
import com.dmd.zsb.mvp.view.MineView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class MinePresenterImpl implements MinePresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private MineView mineView;
    private MineInteractorImpl mineInteractor;

    public MinePresenterImpl(Context mContext, MineView mineView) {
        this.mContext = mContext;
        this.mineView = mineView;
        mineInteractor=new MineInteractorImpl(this);
    }

    @Override
    public void onMine(JsonObject jsonObject) {
        mineInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        mineView.setMineView(data);
    }

    @Override
    public void onError(String msg) {
        mineView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
