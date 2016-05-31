package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.VouchersInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.VouchersPresenter;
import com.dmd.zsb.mvp.view.VouchersView;
import com.dmd.zsb.protocol.request.vouchersRequest;
import com.dmd.zsb.protocol.response.vouchersResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class VouchersPresenterImpl implements VouchersPresenter,BaseMultiLoadedListener<vouchersResponse> {
    private Context mContext;
    private VouchersView vouchersView;
    private VouchersInteractorImpl vouchersInteractor;

    public VouchersPresenterImpl(Context mContext, VouchersView vouchersView) {
        this.mContext = mContext;
        this.vouchersView = vouchersView;
        vouchersInteractor=new VouchersInteractorImpl(this);
    }

    @Override
    public void onVouchers(int event_tag,JSONObject jsonObject) {
        vouchersView.showLoading(null);
        vouchersRequest request=new vouchersRequest();
        try {
            request.fromJson(jsonObject);
            vouchersInteractor.getCommonListData(event_tag,request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(int event_tag,vouchersResponse response) {
        vouchersView.hideLoading();
        if (event_tag== Constants.EVENT_REFRESH_DATA){
            vouchersView.refreshListData(response);
        }else if (event_tag==Constants.EVENT_LOAD_MORE_DATA){
            vouchersView.addMoreListData(response);
        }
    }

    @Override
    public void onError(String msg) {
        vouchersView.hideLoading();
        vouchersView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
