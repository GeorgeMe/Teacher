package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ReceivedOrdersInteractorImpl implements CommonListInteractor {
    private BaseMultiLoadedListener<JsonObject> loadedListener;

    public ReceivedOrdersInteractorImpl(BaseMultiLoadedListener<JsonObject> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(final int event, JsonObject gson) {
        GsonRequest<JsonObject> gsonRequest = new GsonRequest<JsonObject>(UriHelper.getInstance().getReceivedOrders(gson),null,new TypeToken<JsonObject>() {}.getType(),
                new Response.Listener<JsonObject>() {
                    @Override
                    public void onResponse(JsonObject response) {
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
        gsonRequest.setTag("getReceivedOrders");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
