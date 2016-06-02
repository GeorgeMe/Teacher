package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.RechargeInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.RechargePresenter;
import com.dmd.zsb.mvp.view.RechargeView;
import com.dmd.zsb.protocol.request.rechargeRequest;
import com.dmd.zsb.protocol.response.rechargeResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/1.
 */
public class RechargePresenterImpl implements RechargePresenter,BaseSingleLoadedListener<rechargeResponse> {

    private Context mContext;
    private RechargeView rechargeView;
    private RechargeInteractorImpl rechargeInteractor;

    public RechargePresenterImpl(Context mContext, RechargeView rechargeView) {
        this.mContext = mContext;
        this.rechargeView = rechargeView;
        rechargeInteractor=new RechargeInteractorImpl(this);
    }

    @Override
    public void onRecharge(JSONObject jsonObject) {
        rechargeView.showLoading(null);
        rechargeRequest request=new rechargeRequest();
        try {
            request.fromJson(jsonObject);
            rechargeInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(rechargeResponse data) {
        rechargeView.hideLoading();
        if (data.errno==0){
            rechargeView.onRechargeView(data);
        }else {
            rechargeView.showError("系统错误，稍后重试");
        }

    }

    @Override
    public void onError(String msg) {
        rechargeView.hideLoading();
        rechargeView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
