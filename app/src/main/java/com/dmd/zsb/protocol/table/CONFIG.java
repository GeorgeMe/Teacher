package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CONFIG extends SugarRecord implements Serializable {

    public int push;

    public CONFIG() {
        super();
    }

    public CONFIG(int push) {
        super();
        this.push = push;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        JSONArray subItemArray;

        this.push = jsonObject.optInt("push");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("push", push);
        return localItemObject;
    }

}
