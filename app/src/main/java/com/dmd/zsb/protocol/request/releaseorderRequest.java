package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class releaseorderRequest extends SugarRecord implements Serializable {

    public String   appkey;
    public String version;
    public String   sid;
    public String   uid;
    public String   lon;
    public String   lat;
    public String   location;
    public String   offer_price;
    public String   appointment_time;
    public String   text;
    public String   subid;
    public String   default_receiver_id;

    public releaseorderRequest() {
        super();
    }

    public releaseorderRequest(String appkey, String version, String sid, String uid, String lon, String lat, String location, String offer_price, String appointment_time, String text, String subid, String default_receiver_id) {
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.lon = lon;
        this.lat = lat;
        this.location = location;
        this.offer_price = offer_price;
        this.appointment_time = appointment_time;
        this.text = text;
        this.subid = subid;
        this.default_receiver_id = default_receiver_id;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.appkey = jsonObject.optString("appkey");
        this.version = jsonObject.optString("version");
        this.sid = jsonObject.optString("sid");
        this.uid = jsonObject.optString("uid");
        this.lon = jsonObject.optString("lon");
        this.lat = jsonObject.optString("lat");
        this.location = jsonObject.optString("location");
        this.offer_price = jsonObject.optString("offer_price");
        this.appointment_time = jsonObject.optString("appointment_time");
        this.text = jsonObject.optString("text");
        this.subid = jsonObject.optString("subid");
        this.default_receiver_id = jsonObject.optString("default_receiver_id");

        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("lon", lon);
        localItemObject.put("lat", lat);
        localItemObject.put("location", location);
        localItemObject.put("offer_price", offer_price);
        localItemObject.put("appointment_time", appointment_time);
        localItemObject.put("text", text);
        localItemObject.put("subid", subid);
        localItemObject.put("default_receiver_id", default_receiver_id);

        return localItemObject;
    }
}
