package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.EvaluationInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.EvaluationPresenter;
import com.dmd.zsb.mvp.view.EvaluationView;
import com.dmd.zsb.protocol.request.evaluationRequest;
import com.dmd.zsb.protocol.response.evaluationResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class EvaluationPresenterImpl implements EvaluationPresenter,BaseMultiLoadedListener<evaluationResponse> {

    private Context mContext;
    private EvaluationView evaluationView;
    private EvaluationInteractorImpl evaluationInteractor;

    public EvaluationPresenterImpl(Context mContext, EvaluationView evaluationView) {
        this.mContext = mContext;
        this.evaluationView = evaluationView;
        evaluationInteractor=new EvaluationInteractorImpl(this);
    }

    @Override
    public void onEvaluation(int event_tag,JSONObject jsonObject) {
        evaluationView.showLoading(null);
        evaluationRequest request=new evaluationRequest();
        try {
            request.fromJson(jsonObject);
            evaluationInteractor.getCommonListData(event_tag,request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(int event_tag, evaluationResponse response) {
        evaluationView.hideLoading();
        if (event_tag== Constants.EVENT_LOAD_MORE_DATA){
            evaluationView.addMoreListData(response);
        }else if (event_tag==Constants.EVENT_REFRESH_DATA){
            evaluationView.refreshListData(response);
        }
    }

    @Override
    public void onError(String msg) {
        evaluationView.hideLoading();
        evaluationView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        evaluationView.showException(msg);
    }
}
