package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.OrderResponse;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/3/29.
 */
public class OrderInteractorImpl implements CommonListInteractor {
    private BaseMultiLoadedListener<OrderResponse> loadedListener;
    public OrderInteractorImpl(BaseMultiLoadedListener<OrderResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(final int event, JsonObject gson) {
        GsonRequest<OrderResponse> gsonRequest = new GsonRequest<OrderResponse>(UriHelper.getInstance().myorder(gson),null,new TypeToken<OrderResponse>() {}.getType(),
                new Response.Listener<OrderResponse>() {
                    @Override
                    public void onResponse(OrderResponse response) {
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
        gsonRequest.setTag("myorder");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
