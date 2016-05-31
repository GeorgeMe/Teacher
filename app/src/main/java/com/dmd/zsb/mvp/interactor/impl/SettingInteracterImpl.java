package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/25.
 */
public class SettingInteracterImpl implements CommonListInteractor {
    private BaseMultiLoadedListener<JSONObject> loadedListener;
    private OnUploadProcessListener uploadProcessListener;

    public SettingInteracterImpl(BaseMultiLoadedListener<JSONObject> loadedListener, OnUploadProcessListener uploadProcessListener) {
        this.loadedListener = loadedListener;
        this.uploadProcessListener = uploadProcessListener;
    }

    @Override
    public void getCommonListData(final int event, JSONObject json) {
        //event  事件标记   changeAvatar修改头像  signOut退出登录

    }

    public void onSignOut(final int event,JSONObject json) {
        GsonRequest<JSONObject> gsonRequest=new GsonRequest<JSONObject>(UriHelper.getInstance().signOut(json),null,new TypeToken<JSONObject>(){}.getType(), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                loadedListener.onSuccess(event,response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("signOut");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
