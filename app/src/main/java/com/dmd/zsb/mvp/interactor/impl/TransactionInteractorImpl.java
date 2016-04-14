package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.TransactionResponse;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/4/6.
 */
public class TransactionInteractorImpl implements CommonListInteractor {

    private BaseMultiLoadedListener<TransactionResponse> loadedListener;

    public TransactionInteractorImpl(BaseMultiLoadedListener<TransactionResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }


    @Override
    public void getCommonListData(final int event, JsonObject gson) {
        GsonRequest<TransactionResponse> gsonRequest = new GsonRequest<TransactionResponse>(
                UriHelper.getInstance().mytransaction(gson),
                null,
                new TypeToken<TransactionResponse>() {
                }.getType(),
                new Response.Listener<TransactionResponse>() {
                    @Override
                    public void onResponse(TransactionResponse response) {
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
        gsonRequest.setTag("transaction");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
