package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.OrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.OrderPresenter;
import com.dmd.zsb.mvp.view.OrderView;
import com.dmd.zsb.protocol.request.orderRequest;
import com.dmd.zsb.protocol.response.orderResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class OrderPresenterImpl implements OrderPresenter ,BaseMultiLoadedListener<orderResponse>{
    private Context mContext;
    private OrderInteractorImpl orderInteractor;
    private OrderView orderView;

    public OrderPresenterImpl(Context mContext, OrderView orderView) {
        this.mContext = mContext;
        this.orderView = orderView;
        orderInteractor=new OrderInteractorImpl(this);
    }

    @Override
    public void onOrder(int event_tag, JSONObject jsonObject) {
        orderView.showLoading(null);
        orderRequest request=new orderRequest();
        try {
            request.fromJson(jsonObject);
            orderInteractor.getCommonListData(event_tag,request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(int event_tag, orderResponse data) {
        orderView.hideLoading();
        if (event_tag== Constants.EVENT_LOAD_MORE_DATA){
            orderView.addMoreListData(data);
        }else if (event_tag==Constants.EVENT_REFRESH_DATA){
            orderView.refreshListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        orderView.hideLoading();
        orderView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        orderView.showException(msg);
    }
}
