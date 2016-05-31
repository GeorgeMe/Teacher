package com.dmd.zsb.mvp.view;

import android.view.animation.Animation;

public interface SplashView extends BaseView{

    void animateBackgroundImage(Animation animation);

    void initializeViews(String versionName, String copyright, int backgroundResId);

    void initializeUmengConfig();

    void navigateToLead();

    void navigateToHomePage();


}
