package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.NickNameInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.NickNamePresenter;
import com.dmd.zsb.mvp.view.NickNameView;
import com.dmd.zsb.protocol.request.nicknameRequest;
import com.dmd.zsb.protocol.response.nicknameResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class NickNamePresenterImpl implements NickNamePresenter,BaseSingleLoadedListener<nicknameResponse> {
    private NickNameInteractorImpl nickNameInteractor;
    private Context mContext;
    private NickNameView nickNameView;

    public NickNamePresenterImpl(NickNameView nickNameView, Context mContext) {
        this.nickNameView = nickNameView;
        this.mContext = mContext;
        nickNameInteractor=new NickNameInteractorImpl(this);
    }

    @Override
    public void updateNickName(JSONObject jsonObject) {
        nickNameView.showLoading(null);
        nicknameRequest request=new nicknameRequest();
        try {
            request.fromJson(jsonObject);
            nickNameInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(nicknameResponse response) {
        nickNameView.hideLoading();
        nickNameView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        nickNameView.hideLoading();
        nickNameView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
