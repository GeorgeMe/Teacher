package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.WorkDoneImteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.view.DemandView;
import com.dmd.zsb.protocol.request.workdoneOrderRequest;
import com.dmd.zsb.protocol.response.workdoneResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/2.
 */
public class WorkDonePresenterImpl implements WorkDonePresenter,BaseSingleLoadedListener<workdoneResponse> {
    private Context mContext;
    private DemandView demandView;
    private WorkDoneImteractorImpl workDoneImteractor;

    public WorkDonePresenterImpl(Context mContext, DemandView demandView) {
        this.mContext = mContext;
        this.demandView = demandView;
        workDoneImteractor=new WorkDoneImteractorImpl(this);
    }

    @Override
    public void onWorkDone(JSONObject jsonObject) {
        demandView.showLoading(null);
        workdoneOrderRequest request=new workdoneOrderRequest();
        try {
            request.fromJson(jsonObject);
            workDoneImteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(workdoneResponse data) {
        demandView.hideLoading();
        demandView.workdone(data);
    }

    @Override
    public void onError(String msg) {
        demandView.hideLoading();
        demandView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
