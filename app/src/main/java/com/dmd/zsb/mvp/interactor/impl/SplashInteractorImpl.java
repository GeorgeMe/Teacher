package com.dmd.zsb.mvp.interactor.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.entity.response.SplashResponse;
import com.dmd.zsb.mvp.interactor.SplashInteractor;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;

public class SplashInteractorImpl implements SplashInteractor {

    private BaseSingleLoadedListener<SplashResponse> loadedListener;

    public SplashInteractorImpl(BaseSingleLoadedListener<SplashResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void loadingInitData(JsonObject jsonObject) {
        GsonRequest<SplashResponse> gsonRequest=new GsonRequest<SplashResponse>(UriHelper.getInstance().InitData(jsonObject),null,new TypeToken<SplashResponse>(){}.getType(), new Response.Listener<SplashResponse>(){
            @Override
            public void onResponse(SplashResponse response) {
                Log.e("onResponse",""+response.getGradeList().size());
                Log.e("onResponse",""+response.getSubjectList().size());
                loadedListener.onSuccess(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("InitData");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }

    @Override
    public Animation getBackgroundImageAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.splash);
    }

    @Override
    public int getBackgroundImageResID() {
        int resId;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour <= 12) {
            resId = R.drawable.morning;
        } else if (hour > 12 && hour <= 18) {
            resId = R.drawable.afternoon;
        } else {
            resId = R.drawable.night;
        }
        return resId;
    }

    @Override
    public String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.format(context.getResources().getString(R.string.splash_version), versionName);
    }

    @Override
    public String getCopyright(Context context) {
        return context.getResources().getString(R.string.splash_copyright);
    }
}
