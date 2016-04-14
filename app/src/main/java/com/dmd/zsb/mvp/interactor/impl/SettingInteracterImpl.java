package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.FormFile;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.PostUploadRequest;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/25.
 */
public class SettingInteracterImpl implements CommonListInteractor {
    private BaseMultiLoadedListener<JsonObject> loadedListener;
    private OnUploadProcessListener uploadProcessListener;

    public SettingInteracterImpl(BaseMultiLoadedListener<JsonObject> loadedListener, OnUploadProcessListener uploadProcessListener) {
        this.loadedListener = loadedListener;
        this.uploadProcessListener = uploadProcessListener;
    }

    @Override
    public void getCommonListData(final int event, JsonObject gson) {
        //event  事件标记   changeAvatar修改头像  signOut退出登录
        List<FormFile> fileList=new ArrayList<>();
        JsonObject file=gson.get("formFile").getAsJsonObject();
        JsonObject json=gson.get("json").getAsJsonObject();
        FormFile formFile=new FormFile(file.get("fileName").getAsString(), new File(file.get("filePath").getAsString()), file.get("parameterName").getAsString(), file.get("contentType").getAsString());
        fileList.add(formFile);
        PostUploadRequest<JsonObject> uploadRequest=new PostUploadRequest<JsonObject>(UriHelper.getInstance().changeAvatar(json), fileList, new TypeToken<JsonObject>() {
        }.getType(), new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                TLog.e("",response.toString());
                loadedListener.onSuccess(event,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TLog.e("",error.getMessage());
                loadedListener.onError(error.getMessage());
            }
        });
        uploadRequest.setOnUploadProcessListener(uploadProcessListener);
        uploadRequest.setShouldCache(true);
        uploadRequest.setTag("changeAvatar");
        VolleyHelper.getInstance().getRequestQueue().add(uploadRequest);
    }

    public void onSignOut(final int event,JsonObject jsonObject) {
        GsonRequest<JsonObject> gsonRequest=new GsonRequest<JsonObject>(UriHelper.getInstance().signOut(jsonObject),null,new TypeToken<JsonObject>(){}.getType(), new Response.Listener<JsonObject>(){
            @Override
            public void onResponse(JsonObject response) {
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
