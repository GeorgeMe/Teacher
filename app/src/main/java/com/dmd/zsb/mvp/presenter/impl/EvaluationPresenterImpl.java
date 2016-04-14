package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.response.EvaluationResponse;
import com.dmd.zsb.mvp.interactor.impl.EvaluationInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.EvaluationPresenter;
import com.dmd.zsb.mvp.view.EvaluationView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class EvaluationPresenterImpl implements EvaluationPresenter,BaseMultiLoadedListener<EvaluationResponse> {

    private Context mContext;
    private EvaluationView evaluationView;
    private EvaluationInteractorImpl evaluationInteractor;

    public EvaluationPresenterImpl(Context mContext, EvaluationView evaluationView) {
        this.mContext = mContext;
        this.evaluationView = evaluationView;
        evaluationInteractor=new EvaluationInteractorImpl(this);
    }

    @Override
    public void onEvaluation(int event_tag,JsonObject jsonObject) {
        evaluationInteractor.getCommonListData(event_tag,jsonObject);
    }

    @Override
    public void onSuccess(int event_tag, EvaluationResponse data) {
        if (event_tag== Constants.EVENT_LOAD_MORE_DATA){
            evaluationView.addMoreListData(data);
        }else if (event_tag==Constants.EVENT_REFRESH_DATA){
            evaluationView.refreshListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        evaluationView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        evaluationView.showException(msg);
    }
}
