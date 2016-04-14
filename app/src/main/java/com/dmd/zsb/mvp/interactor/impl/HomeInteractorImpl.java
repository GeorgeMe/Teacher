package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;

import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2015/12/15.
 */
public class HomeInteractorImpl implements CommonListInteractor {

    private BaseMultiLoadedListener<HomeResponse> loadedListener = null;
    public HomeInteractorImpl(BaseMultiLoadedListener<HomeResponse> loadedListener){
        this.loadedListener=loadedListener;
    }
    @Override
    public void getCommonListData(final int event,final JsonObject data) {
        GsonRequest<HomeResponse> gsonRequest = new GsonRequest<HomeResponse>(
                UriHelper.getInstance().home(data),
                null,
                new TypeToken<HomeResponse>() {
                }.getType(),
                new Response.Listener<HomeResponse>() {
                    @Override
                    public void onResponse(HomeResponse response) {
                        loadedListener.onSuccess(event, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadedListener.onException(error.getMessage());
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("demand");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
