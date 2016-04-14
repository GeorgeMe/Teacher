package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.mvp.interactor.impl.HomeInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.HomePresenter;
import com.dmd.zsb.mvp.view.HomeView;
import com.google.gson.JsonObject;


/**
 * Created by Administrator on 2015/12/15.
 */
public class HomePresenterImpl implements HomePresenter,BaseMultiLoadedListener<HomeResponse> {
//keytool -v -list -keystore keystore.jks
    private Context mContext=null;
    private HomeView mHomeView=null;
    private HomeInteractorImpl mCommonListInteractor = null;

    public HomePresenterImpl(Context mContext,HomeView mHomeView){
        this.mContext=mContext;
        this.mHomeView=mHomeView;
        mCommonListInteractor=new HomeInteractorImpl(this);
    }
    @Override
    public void loadListData(int event,JsonObject data) {
        mHomeView.hideLoading();
        if (event==Constants.EVENT_REFRESH_DATA) {
            mHomeView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        //提交的参数封装
        mCommonListInteractor.getCommonListData(event,data);
    }
    
    @Override
    public void onSuccess(int event_tag, HomeResponse data) {
        mHomeView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mHomeView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mHomeView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mHomeView.hideLoading();
        mHomeView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mHomeView.hideLoading();
        mHomeView.showError(msg);
    }

}
