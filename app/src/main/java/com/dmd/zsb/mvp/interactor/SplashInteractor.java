package com.dmd.zsb.mvp.interactor;

import android.content.Context;
import android.view.animation.Animation;

import com.google.gson.JsonObject;

public interface SplashInteractor {

    int getBackgroundImageResID();

    String getCopyright(Context context);

    String getVersionName(Context context);

    Animation getBackgroundImageAnimation(Context context);

    void loadingInitData(JsonObject jsonObject);
}
