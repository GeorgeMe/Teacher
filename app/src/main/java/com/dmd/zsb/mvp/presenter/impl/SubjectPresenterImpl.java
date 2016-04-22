package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.SubjectInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SubjectPresenter;
import com.dmd.zsb.mvp.view.SubjectView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/19.
 */
public class SubjectPresenterImpl implements SubjectPresenter,BaseSingleLoadedListener<JsonObject> {
    private SubjectInteractorImpl subjectInteractor;
    private SubjectView subjectView;
    private Context mContext;

    public SubjectPresenterImpl(Context mContext, SubjectView subjectView) {
        this.mContext = mContext;
        this.subjectView = subjectView;
        subjectInteractor=new SubjectInteractorImpl(this);
    }

    @Override
    public void onChangeSubject(JsonObject jsonObject) {
        subjectInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        subjectView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        subjectView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
