package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.listeners.CommonSingleInteractor;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CertifyInteractorImpl implements CommonSingleInteractor{
    private BaseSingleLoadedListener<JsonObject> loadedListener;

    public CertifyInteractorImpl(BaseSingleLoadedListener<JsonObject> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonSingleData(JsonObject gson) {
        GsonRequest<JsonObject> gsonRequest = new GsonRequest<JsonObject>(UriHelper.getInstance().certify(gson), null, new TypeToken<JsonObject>() {
        }.getType(), new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                loadedListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("certify");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
