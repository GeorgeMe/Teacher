package com.dmd.zsb.mvp.interactor;

import android.content.Context;
import android.view.animation.Animation;

public interface SplashInteractor {

    int getBackgroundImageResID();

    String getCopyright(Context context);

    String getVersionName(Context context);

    Animation getBackgroundImageAnimation(Context context);
}
