package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.LogFileInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.LogFilePresenter;
import com.dmd.zsb.mvp.view.LogFileView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class LogFilePresenterImpl implements LogFilePresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private LogFileView logFileView;
    private LogFileInteractorImpl logFileInteractor;

    public LogFilePresenterImpl(Context mContext, LogFileView logFileView) {
        this.mContext = mContext;
        this.logFileView = logFileView;
        logFileInteractor=new LogFileInteractorImpl(this);
    }

    @Override
    public void onLogFile(JsonObject jsonObject) {
        logFileInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        logFileView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
