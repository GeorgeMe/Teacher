package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.MinekInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.MinePresenter;
import com.dmd.zsb.mvp.view.MineView;
import com.dmd.zsb.protocol.request.mineRequest;
import com.dmd.zsb.protocol.response.mineResponse;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/4/3.
 */
public class MinePresenterImpl implements MinePresenter,BaseSingleLoadedListener<mineResponse> {

    private Context mContext;
    private MinekInteractorImpl minekInteractor;
    private MineView mineView;

    public MinePresenterImpl(Context mContext, MineView mineView) {
        this.mContext = mContext;
        this.mineView = mineView;
        minekInteractor=new MinekInteractorImpl(this);
    }

    @Override
    public void onSuccess(mineResponse response) {
        mineView.hideLoading();
        if (response.errno==0){
            mineView.setView(response);
        }
    }

    @Override
    public void onError(String msg) {
        mineView.hideLoading();
        mineView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void onMineInfo() {
        mineView.showLoading(null);
        mineRequest request=new mineRequest();
        request.appkey = Constants.ZSBAPPKEY;
        request.version = Constants.ZSBVERSION;
        request.uid = XmlDB.getInstance(mContext).getKeyString("uid","uid");
        request.sid = XmlDB.getInstance(mContext).getKeyString("sid","sid");
        try {
            minekInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }
}
