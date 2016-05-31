package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.HomeInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.HomePresenter;
import com.dmd.zsb.mvp.view.HomeView;
import com.dmd.zsb.protocol.request.homeRequest;
import com.dmd.zsb.protocol.response.homeResponse;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/12/15.
 */
public class HomePresenterImpl implements HomePresenter,BaseMultiLoadedListener<homeResponse> {
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
    public void loadListData(int event,JSONObject jsonObject) {
        mHomeView.showLoading(null);
        homeRequest request=new homeRequest();
        try {
            request.fromJson(jsonObject);
            mCommonListInteractor.getCommonListData(event,request.toJson());
        }catch (JSONException j){

        }
    }
    @Override
    public void onSuccess(int event_tag, homeResponse response) {
        mHomeView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mHomeView.refreshListData(response);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mHomeView.addMoreListData(response);
        }
    }

    @Override
    public void onError(String msg) {
        mHomeView.hideLoading();
        mHomeView.showError(msg);
    }

    @Override
    public void onException(String msg) {
       onError(msg);
    }

}
