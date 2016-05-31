package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;
import android.view.animation.Animation;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.mvp.interactor.SplashInteractor;
import com.dmd.zsb.mvp.interactor.impl.SplashInteractorImpl;
import com.dmd.zsb.mvp.presenter.Presenter;
import com.dmd.zsb.mvp.view.SplashView;


public class SplashPresenterImpl implements Presenter{

    private Context mContext = null;
    private SplashView mSplashView = null;
    private SplashInteractor mSplashInteractor = null;


    public SplashPresenterImpl(Context context, SplashView splashView) {
        if (null == splashView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }
        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void initialized() {
        mSplashView.initializeUmengConfig();
        mSplashView.initializeViews(mSplashInteractor.getVersionName(mContext),
                mSplashInteractor.getCopyright(mContext),
                mSplashInteractor.getBackgroundImageResID());
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isFirstRunLead", true)) {
            XmlDB.getInstance(mContext).saveKey("isFirstRunLead", false);
            mSplashView.navigateToLead();
        } else {
            Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //计时 5秒后进入主页
                  mSplashView.navigateToHomePage();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mSplashView.animateBackgroundImage(animation);
        }

    }
}
