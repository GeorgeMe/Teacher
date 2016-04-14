package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.presenter.Presenter;
import com.dmd.zsb.mvp.view.MainView;


public class MainViewImpl implements Presenter {
    private Context mContext=null;
    private MainView mMainView=null;
    public MainViewImpl(Context context,MainView mainView) {
        mContext=context;
        mMainView=mainView;
    }

    @Override
    public void initialized() {
        mMainView.initTabView();
    }
}
