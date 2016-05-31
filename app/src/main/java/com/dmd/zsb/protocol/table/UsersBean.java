package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UsersBean  extends SugarRecord implements Serializable {

    public String user_id;
    public String avatar;
    public String username;
    public String gender;
    public String signature;
    public double goodrate;
    public String lat;
    public String lon;
    public double total_hours;

    public UsersBean() {
        super();
    }

    public UsersBean(String user_id, String avatar,String username,String gender, String signature, double goodrate, String lat, String lon, double total_hours) {
        this.user_id = user_id;
        this.avatar = avatar;
        this.username = username;
        this.gender = gender;
        this.signature = signature;
        this.goodrate = goodrate;
        this.lat = lat;
        this.lon = lon;
        this.total_hours = total_hours;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.user_id = jsonObject.optString("user_id");
        this.avatar = jsonObject.optString("avatar");
        this.username = jsonObject.optString("username");
        this.gender = jsonObject.optString("gender");
        this.signature = jsonObject.optString("signature");
        this.goodrate = jsonObject.optInt("goodrate");
        this.lat = jsonObject.optString("lon");
        this.lat = jsonObject.optString("lat");
        this.total_hours = jsonObject.optInt("total_hours");

        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("user_id", user_id);
        localItemObject.put("avatar", avatar);
        localItemObject.put("username", username);
        localItemObject.put("gender", gender);
        localItemObject.put("signature", signature);
        localItemObject.put("goodrate", goodrate);
        localItemObject.put("lon", lon);
        localItemObject.put("lat", lat);
        localItemObject.put("total_hours", total_hours);

        return localItemObject;
    }


}
