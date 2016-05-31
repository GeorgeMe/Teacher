package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.protocol.response.transactionsResponse;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/13.
 */
public class TransactionInteractorImpl  implements CommonListInteractor {
    private BaseMultiLoadedListener<transactionsResponse> loadedListener;

    public TransactionInteractorImpl(BaseMultiLoadedListener<transactionsResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(final int event, JSONObject json) {
        GsonRequest<transactionsResponse> gsonRequest = new GsonRequest<transactionsResponse>(UriHelper.getInstance().billdetail(json),null,new TypeToken<transactionsResponse>() {}.getType(),
                new Response.Listener<transactionsResponse>() {
                    @Override
                    public void onResponse(transactionsResponse response) {
                        loadedListener.onSuccess(event, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError){
                            loadedListener.onError("请求时出现网络错误。");
                        }else if (error instanceof ServerError){
                            loadedListener.onError("服务器内部错误，请稍后重试");
                        }else if (error instanceof NoConnectionError){
                            loadedListener.onError("连接的错误。");
                        }else if (error instanceof TimeoutError){
                            loadedListener.onError("连接超时");
                        }else if (error instanceof ParseError){
                            loadedListener.onError("服务器的响应不能被解析。");
                        }else if (error instanceof AuthFailureError){
                            loadedListener.onError("指示在执行请求时有一个身份验证失败的错误。");
                        }else {
                            loadedListener.onError(error.getMessage());
                        }
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("billdetail");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
