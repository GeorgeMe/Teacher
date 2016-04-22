package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.mvp.interactor.impl.SignUpInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignUpPresenter;
import com.dmd.zsb.mvp.view.SignUpView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SignUpPresenterImpl implements SignUpPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext;
    private SignUpView signUpView;
    private SignUpInteractorImpl signUpInteractor;

    public SignUpPresenterImpl(Context mContext, SignUpView signUpView) {
        this.mContext = mContext;
        this.signUpView = signUpView;
        signUpInteractor=new SignUpInteractorImpl(this);
    }

    @Override
    public void onSignUp(JsonObject jsonObject) {
        signUpInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        if (data.get("msg").getAsString().equals("fail")){
            onError("登录失败");
        }else {
            XmlDB.getInstance(mContext).saveKey("uid",data.get("id").getAsString());
            XmlDB.getInstance(mContext).saveKey("sid",data.get("sid").getAsString());
            XmlDB.getInstance(mContext).saveKey("isLogin", true);
            signUpView.navigateToHome();
        }
    }

    @Override
    public void onError(String msg) {
        signUpView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
