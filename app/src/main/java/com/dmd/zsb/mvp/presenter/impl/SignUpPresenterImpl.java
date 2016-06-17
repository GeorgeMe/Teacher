package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.SignUpInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignUpPresenter;
import com.dmd.zsb.mvp.view.SignUpView;
import com.dmd.zsb.protocol.request.signupRequest;
import com.dmd.zsb.protocol.response.signupResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignUpPresenterImpl implements SignUpPresenter,BaseSingleLoadedListener<signupResponse> {
    private Context mContext=null;
    private SignUpView signUpView;
    private SignUpInteractorImpl signUpInteractor;

    public SignUpPresenterImpl(Context mContext, SignUpView signUpView) {
        this.mContext = mContext;
        this.signUpView = signUpView;
        signUpInteractor=new SignUpInteractorImpl(this);
    }

    @Override
    public void signUp(JSONObject jsonObject) {
        signUpView.showLoading(null);
        signupRequest request=new signupRequest();
        try {
            request.fromJson(jsonObject);
            signUpInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(signupResponse response) {
        signUpView.hideLoading();
        if (response.msg.equals("fail")){
            onError("登录失败");
        }else {
            signUpView.navigateToHome(response);
        }
    }

    @Override
    public void onError(String msg) {
        signUpView.hideLoading();
        signUpView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
