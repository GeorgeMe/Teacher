package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.FeedbackInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.FeedbackPresenter;
import com.dmd.zsb.mvp.view.FeedbackView;
import com.dmd.zsb.protocol.request.feedbackRequest;
import com.dmd.zsb.protocol.response.feedbackResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/25.
 */
public class FeedbackPresenterImpl implements FeedbackPresenter,BaseSingleLoadedListener<feedbackResponse> {
    private Context mContext=null;
    private FeedbackInteractorImpl feedbackInteractor;
    private FeedbackView feedbackView;

    public FeedbackPresenterImpl(Context mContext, FeedbackView feedbackView) {
        this.mContext = mContext;
        this.feedbackView = feedbackView;
        feedbackInteractor=new FeedbackInteractorImpl(this);
    }

    @Override
    public void seedFeedback(JSONObject jsonObject) {
        feedbackView.showLoading(null);
        feedbackRequest request=new feedbackRequest();
        try {
            request.fromJson(jsonObject);
            feedbackInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(feedbackResponse data) {
        feedbackView.hideLoading();
        feedbackView.navigateToSetting(data);
    }

    @Override
    public void onError(String msg) {
        feedbackView.hideLoading();
        feedbackView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
