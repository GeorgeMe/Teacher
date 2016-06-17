package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.zsb.mvp.interactor.impl.AttestationInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.AttestationPresenter;
import com.dmd.zsb.mvp.view.AttestationView;
import com.dmd.zsb.protocol.request.attestationRequest;
import com.dmd.zsb.protocol.response.attestationResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/17.
 */
public class AttestationPresenterImpl implements AttestationPresenter,BaseSingleLoadedListener<attestationResponse>{
    private Context mContext;
    private AttestationView attestationView;
    private AttestationInteractorImpl attestationInteractor;
    private OnUploadProcessListener uploadProcessListener;

    public AttestationPresenterImpl(Context mContext, AttestationView attestationView, OnUploadProcessListener uploadProcessListener) {
        this.mContext = mContext;
        this.attestationView = attestationView;
        this.uploadProcessListener = uploadProcessListener;
        attestationInteractor=new AttestationInteractorImpl(this,uploadProcessListener);
    }

    @Override
    public void onAttestation(JSONObject jsonObject,JSONObject fileJson) {
        Log.e("fileJson",fileJson.toString());
        attestationView.showLoading(null);
        try {
            attestationRequest request=new attestationRequest();
            request.fromJson(jsonObject);
            attestationInteractor.getCommonSingleData(request.toJson(),fileJson);
        }catch (JSONException j){
            attestationView.hideLoading();
        }

    }

    @Override
    public void onSuccess(attestationResponse data) {
        attestationView.onAttestation(data);
    }

    @Override
    public void onError(String msg) {
        attestationView.hideLoading();
        //attestationView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
