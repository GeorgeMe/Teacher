package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ProfileInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ProfilePresenter;
import com.dmd.zsb.mvp.view.ProfileView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ProfilePresenterImpl implements ProfilePresenter,BaseSingleLoadedListener<JsonObject> {
    private ProfileInteractorImpl briefInteractor;
    private Context mContext;
    private ProfileView profileView;

    public ProfilePresenterImpl(ProfileView profileView, Context mContext) {
        this.profileView = profileView;
        this.mContext = mContext;
        briefInteractor=new ProfileInteractorImpl(this);
    }

    @Override
    public void onChangeProfile(JsonObject jsonObject) {
        briefInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        profileView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        profileView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
