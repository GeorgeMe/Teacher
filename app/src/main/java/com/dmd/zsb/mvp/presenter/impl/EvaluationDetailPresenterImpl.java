package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.EvaluationDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.EvaluationDetailPresenter;
import com.dmd.zsb.mvp.view.EvaluationDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class EvaluationDetailPresenterImpl implements EvaluationDetailPresenter ,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private EvaluationDetailView evaluationDetailView;
    private EvaluationDetailInteractorImpl evaluationDetailInteractor;

    public EvaluationDetailPresenterImpl(Context mContext, EvaluationDetailView evaluationDetailView) {
        this.mContext = mContext;
        this.evaluationDetailView = evaluationDetailView;
        evaluationDetailInteractor=new EvaluationDetailInteractorImpl(this);
    }

    @Override
    public void onEvaluationDetail(JsonObject jsonObject) {
        evaluationDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {

    }

    @Override
    public void onError(String msg) {
        evaluationDetailView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
