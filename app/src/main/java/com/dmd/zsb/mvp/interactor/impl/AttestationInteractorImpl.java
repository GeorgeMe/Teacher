package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.AuthFailureError;
import com.android.volley.FormFile;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.PostUploadRequest;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.protocol.request.changeavatarRequest;
import com.dmd.zsb.protocol.response.attestationResponse;
import com.dmd.zsb.protocol.table.FORMFILE;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class AttestationInteractorImpl{
    private BaseSingleLoadedListener<attestationResponse> loadedListener;
    private OnUploadProcessListener uploadProcessListener;

    public AttestationInteractorImpl(BaseSingleLoadedListener<attestationResponse> loadedListener, OnUploadProcessListener uploadProcessListener) {
        this.loadedListener = loadedListener;
        this.uploadProcessListener = uploadProcessListener;
    }


    public void getCommonSingleData(JSONObject jsonObject,JSONObject file) {
        List<FormFile> fileList=new ArrayList<>();
        changeavatarRequest request=new changeavatarRequest();
        FORMFILE formfile=new FORMFILE();

        try {
            formfile.fromJson(file);
            request.fromJson(jsonObject);
        }catch (JSONException j){

        }
        FormFile formFile=new FormFile(formfile.fileName, new File(formfile.filePath), formfile.parameterName, formfile.contentType);
        fileList.add(formFile);

        PostUploadRequest<attestationResponse> uploadRequest=new PostUploadRequest<attestationResponse>(UriHelper.getInstance().certify(jsonObject), fileList, new TypeToken<attestationResponse>() {
        }.getType(), new Response.Listener<attestationResponse>() {
            @Override
            public void onResponse(attestationResponse response) {
                loadedListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
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
        });
        uploadRequest.setOnUploadProcessListener(uploadProcessListener);
        uploadRequest.setShouldCache(true);
        uploadRequest.setTag("certify");
        VolleyHelper.getInstance().getRequestQueue().add(uploadRequest);
    }
}
