package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.UsersBean;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class signupResponse extends SugarRecord implements Serializable {

    public String   sid;
    public String   uid;
    public int succeed;
    public String msg;
    public UsersBean user;

    public signupResponse() {
        super();
    }

    public signupResponse(String sid, String uid, int succeed, String msg, UsersBean user) {
        super();
        this.sid = sid;
        this.uid = uid;
        this.succeed = succeed;
        this.msg = msg;
        this.user = user;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.sid = jsonObject.optString("sid");
        this.uid = jsonObject.optString("uid");
        this.succeed = jsonObject.optInt("succeed");
        this.msg = jsonObject.optString("msg");
        UsersBean user=new UsersBean();
        user.fromJson(jsonObject.optJSONObject("user"));
        this.user=user;
        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("succeed", succeed);
        localItemObject.put("msg", msg);
        if (user!=null){
            localItemObject.put("user",user.toJson());
        }
        return localItemObject;
    }
}
