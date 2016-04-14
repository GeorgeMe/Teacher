package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.WorkdoneInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.WorkdonePresenter;
import com.dmd.zsb.mvp.view.WorkdoneView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class WorkdonePresenterImpl implements WorkdonePresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private WorkdoneView workdoneView;
    private WorkdoneInteractorImpl workdoneInteractor;

    public WorkdonePresenterImpl(Context mContext, WorkdoneView workdoneView) {
        this.mContext = mContext;
        this.workdoneView = workdoneView;
        workdoneInteractor=new WorkdoneInteractorImpl(this);
    }

    @Override
    public void onWorkdone(JsonObject jsonObject) {
        workdoneInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        workdoneView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
