package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class signupRequest extends SugarRecord implements Serializable {

    public String   appkey;
    public String version;
    public String   client_type;
    public String   location;
    public String   lat;
    public String   lon;
    public String   role;
    public String   nickname;
    public String   mobile;
    public String   password;

    public signupRequest() {
        super();
    }

    public signupRequest(String appkey, String version, String client_type, String location, String lat, String lon, String role, String nickname, String mobile, String password) {
        this.appkey = appkey;
        this.version = version;
        this.client_type = client_type;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.role = role;
        this.nickname = nickname;
        this.mobile = mobile;
        this.password = password;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.appkey = jsonObject.optString("appkey");
        this.version = jsonObject.optString("version");
        this.client_type = jsonObject.optString("client_type");
        this.location = jsonObject.optString("location");
        this.lat = jsonObject.optString("lat");
        this.lon = jsonObject.optString("lon");
        this.role = jsonObject.optString("role");
        this.nickname = jsonObject.optString("nickname");
        this.mobile = jsonObject.optString("mobile");
        this.password = jsonObject.optString("password");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("client_type", client_type);
        localItemObject.put("location", location);
        localItemObject.put("lat", lat);
        localItemObject.put("lon", lon);
        localItemObject.put("role", role);
        localItemObject.put("nickname", nickname);
        localItemObject.put("mobile", mobile);
        localItemObject.put("password", password);

        return localItemObject;
    }
}
