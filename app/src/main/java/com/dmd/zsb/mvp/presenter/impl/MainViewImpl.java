package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.MainViewInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.Presenter;
import com.dmd.zsb.mvp.view.MainView;
import com.dmd.zsb.protocol.request.initdataRequest;
import com.dmd.zsb.protocol.response.initdataResponse;
import com.dmd.zsb.protocol.table.GradesBean;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.orm.SugarRecord;

import org.json.JSONException;

import java.util.List;


public class MainViewImpl implements Presenter, BaseSingleLoadedListener<initdataResponse> {
    private Context mContext=null;
    private MainView mMainView=null;
    private MainViewInteractorImpl mainViewInteractor;
    public MainViewImpl(Context context,MainView mainView) {
        mContext=context;
        mMainView=mainView;
        mainViewInteractor=new MainViewInteractorImpl(this);
    }

    @Override
    public void initialized() {
        mMainView.showLoading(null);
        mMainView.initTabView();
        try{
            initdataRequest request=new initdataRequest();
            request.appkey= Constants.ZSBAPPKEY;
            request.version=Constants.ZSBVERSION;
            request.db_version=XmlDB.getInstance(mContext).getKeyIntValue("db_version",0);
            mainViewInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(initdataResponse data) {
        mMainView.hideLoading();
        if (data.errno == 0) {
            if (data.db_version > XmlDB.getInstance(mContext).getKeyIntValue("db_version",0)){
                if (data.grades.size()>0){
                    GradesBean.deleteAll(GradesBean.class);
                    List<GradesBean> grades=data.grades;
                    SugarRecord.saveInTx(grades);
                }
                if (data.subjects.size()>0){
                    SubjectsBean.deleteAll(SubjectsBean.class);
                    List<SubjectsBean> subjects=data.subjects;
                    SugarRecord.saveInTx(subjects);
                }
                XmlDB.getInstance(mContext).saveKey("db_version",data.db_version);
            }
        }
    }

    @Override
    public void onError(String msg) {
        mMainView.hideLoading();
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
