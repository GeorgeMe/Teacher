package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.response.VouchersResponse;
import com.dmd.zsb.mvp.interactor.impl.VouchersInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.VouchersPresenter;
import com.dmd.zsb.mvp.view.VouchersView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class VouchersPresenterImpl implements VouchersPresenter,BaseMultiLoadedListener<VouchersResponse> {
    private Context mContext;
    private VouchersView vouchersView;
    private VouchersInteractorImpl vouchersInteractor;

    public VouchersPresenterImpl(Context mContext, VouchersView vouchersView) {
        this.mContext = mContext;
        this.vouchersView = vouchersView;
        vouchersInteractor=new VouchersInteractorImpl(this);
    }

    @Override
    public void onVouchers(int event_tag,JsonObject jsonObject) {
        vouchersInteractor.getCommonListData(event_tag,jsonObject);
    }

    @Override
    public void onSuccess(int event_tag,VouchersResponse data) {
        if (event_tag== Constants.EVENT_REFRESH_DATA){
            vouchersView.refreshListData(data);
        }else if (event_tag==Constants.EVENT_LOAD_MORE_DATA){
            vouchersView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        vouchersView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
