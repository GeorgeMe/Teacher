package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/13.
 */
public class WithDrawalBean extends SugarRecord implements Serializable {

    public String id;//图片地址
    public String created_at;//图片地址
    public String amount;//说明
    public String category;//有效期
    public String user;

    public WithDrawalBean() {
        super();
    }

    public WithDrawalBean(String id, String created_at, String amount, String category, String user) {
        super();
        this.id = id;
        this.created_at = created_at;
        this.amount = amount;
        this.category = category;
        this.user = user;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.id = jsonObject.optString("id");
        this.created_at = jsonObject.optString("created_at");
        this.amount = jsonObject.optString("amount");
        this.category = jsonObject.optString("category");
        this.user = jsonObject.optString("user");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("id", id);
        localItemObject.put("created_at", created_at);
        localItemObject.put("amount", amount);
        localItemObject.put("category", category);
        localItemObject.put("user", user);

        return localItemObject;
    }
}
