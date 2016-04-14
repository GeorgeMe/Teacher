package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.FeedbackInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.FeedbackPresenter;
import com.dmd.zsb.mvp.view.FeedbackView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/25.
 */
public class FeedbackPresenterImpl implements FeedbackPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext=null;
    private FeedbackInteractorImpl feedbackInteractor;
    private FeedbackView feedbackView;

    public FeedbackPresenterImpl(Context mContext, FeedbackView feedbackView) {
        this.mContext = mContext;
        this.feedbackView = feedbackView;
        feedbackInteractor=new FeedbackInteractorImpl(this);
    }

    @Override
    public void seedFeedback(JsonObject jsonObject) {
        feedbackInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        feedbackView.navigateToHome();
    }

    @Override
    public void onError(String msg) {
        feedbackView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
