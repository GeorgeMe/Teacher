package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ChangePasswordInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ChangePasswordPresenter;
import com.dmd.zsb.mvp.view.ChangePasswordView;
import com.dmd.zsb.protocol.request.changepasswordRequest;
import com.dmd.zsb.protocol.response.changepasswordResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ChangePasswordPresenterImpl implements ChangePasswordPresenter,BaseSingleLoadedListener<changepasswordResponse> {
    private ChangePasswordInteractorImpl changePasswordInteractor;
    private Context mContext;
    private ChangePasswordView changePasswordView;

    public ChangePasswordPresenterImpl(ChangePasswordView changePasswordView, Context mContext) {
        this.changePasswordView = changePasswordView;
        this.mContext = mContext;
        changePasswordInteractor=new ChangePasswordInteractorImpl(this);
    }

    @Override
    public void onChangePassword(JSONObject jsonObject) {
        changePasswordView.showLoading(null);
        changepasswordRequest request=new changepasswordRequest();
        try {
            request.fromJson(jsonObject);
            changePasswordInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(changepasswordResponse data) {
        changePasswordView.hideLoading();
        changePasswordView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        changePasswordView.hideLoading();
        changePasswordView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
