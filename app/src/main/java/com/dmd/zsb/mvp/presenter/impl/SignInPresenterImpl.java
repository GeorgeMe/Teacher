package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.SignInInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignInPresenter;
import com.dmd.zsb.mvp.view.SignInView;
import com.dmd.zsb.protocol.request.signinRequest;
import com.dmd.zsb.protocol.response.signinResponse;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignInPresenterImpl implements SignInPresenter,BaseSingleLoadedListener<signinResponse>{
    private Context mContext=null;
    private SignInView signInView;
    private SignInInteractorImpl signInInteractor;

    public SignInPresenterImpl(Context mContext, SignInView signInView) {
        this.mContext = mContext;
        this.signInView = signInView;
        signInInteractor=new SignInInteractorImpl(this);
    }

    @Override
    public void onSuccess(signinResponse response) {
        signInView.hideLoading();
        if (response.errno==0){
            XmlDB.getInstance(mContext).saveKey("uid",response.uid);
            XmlDB.getInstance(mContext).saveKey("sid",response.sid);
            signInView.navigateToHome();
        }else {
            onError("登录失败");
        }
    }

    @Override
    public void onError(String msg) {
        signInView.hideLoading();
        XmlDB.getInstance(mContext).saveKey("isLogin", false);
        signInView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void signIn(String mobile, String password) {
        signInView.showLoading(null);
        signinRequest request = new signinRequest();
        request.appkey =Constants.ZSBAPPKEY;
        request.mobile =mobile;
        request.password = password;
        request.client_type = Constants.PLATFORM;
        request.role = Constants.USER_ROLE;
        request.location = XmlDB.getInstance(mContext).getKeyString("addr","未取得定位地址");
        request.version = Constants.ZSBVERSION;
        request.lat = LocationManager.getLatitude();
        request.lon = LocationManager.getLongitude();
        try {
            signInInteractor.getCommonSingleData(request.toJson());
        } catch (JSONException e) {

        }
    }
}
