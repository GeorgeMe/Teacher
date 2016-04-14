package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.FormFile;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.google.gson.Gson;



public class PostUploadRequest<T> extends Request<T> {
    private static final String TAG = PostUploadRequest.class.getSimpleName();
    private Listener<T> mListener;
    private List<FormFile> mListItem;
    private String BOUNDARY = "--------------520-13-14";
    private String MULTIPART_FORM_DATA = "multipart/form-data";
    private OnUploadProcessListener onUploadProcessListener;
    private Gson mGson = null;
    private Type mType = null;

    public void setOnUploadProcessListener(OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }
    public PostUploadRequest(String url, List<FormFile> listItem, Type type, Listener<T> listener,
                             ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mListItem = listItem;
        this.mType=type;
        mGson = new Gson();
        //设置请求的响应事件，因为文件上传需要较长的时间，所以在这里加大了，设为5秒
        setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * 这里开始解析数据
     *
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
            VolleyLog.d(TAG, "response ---> " + jsonString);
            return Response.success((T) mGson.fromJson(jsonString, mType), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(e));
        }
    }

    /**
     * 回调正确的数据
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mListItem == null || mListItem.size() == 0) {
            return super.getBody();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            for (FormFile uploadFile : mListItem) {
                StringBuilder fileEntity = new StringBuilder();
                fileEntity.append("--");
                fileEntity.append(BOUNDARY);
                fileEntity.append("\r\n");
                fileEntity.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFileName() + "\"\r\n");
                fileEntity.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");

                bos.write(fileEntity.toString().getBytes());

                onUploadProcessListener.initUpload((int)uploadFile.getFile().length());

                int curLen = 0;
                if (uploadFile.getInStream() != null) {
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
                        curLen += len;
                        bos.write(buffer, 0, len);
                        onUploadProcessListener.onUploadProcess(curLen);
                    }
                    uploadFile.getInStream().close();
                } else {
                    bos.write(uploadFile.getData(), 0, uploadFile.getData().length);
                }
                bos.write("\r\n".getBytes());
                onUploadProcessListener.onUploadDone(11,"上传完成");
            }
            String endLine = "--" + BOUNDARY+"--";
            bos.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            onUploadProcessListener.onUploadDone(12,"上传失败");
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
    }
}
