package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class nicknameResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;

    public nicknameResponse() {
        super();
    }

    public nicknameResponse(int errno, String msg) {
        super();
        this.errno = errno;
        this.msg = msg;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);

        return localItemObject;
    }
}
