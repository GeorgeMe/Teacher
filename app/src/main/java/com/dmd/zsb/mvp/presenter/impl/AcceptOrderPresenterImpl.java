package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.AcceptOrderImteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.AcceptOrderPresenter;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.dmd.zsb.protocol.request.acceptOrderRequest;
import com.dmd.zsb.protocol.response.acceptOrderResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/2.
 */
public class AcceptOrderPresenterImpl implements AcceptOrderPresenter,BaseSingleLoadedListener<acceptOrderResponse> {
    private Context mContext;
    private UserDetailView userDetailView;
    private AcceptOrderImteractorImpl acceptOrderImteractor;

    public AcceptOrderPresenterImpl(Context mContext, UserDetailView userDetailView) {
        this.mContext = mContext;
        this.userDetailView = userDetailView;
        acceptOrderImteractor=new AcceptOrderImteractorImpl(this);
    }

    @Override
    public void onAcceptOrder(JSONObject jsonObject) {
        userDetailView.showLoading(null);
        acceptOrderRequest request=new acceptOrderRequest();
        try {
            request.fromJson(jsonObject);
            acceptOrderImteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(acceptOrderResponse data) {
        userDetailView.hideLoading();
        userDetailView.acceptOrder(data);
    }

    @Override
    public void onError(String msg) {
        userDetailView.hideLoading();
        userDetailView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
