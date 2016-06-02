package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DemandsBean extends SugarRecord implements Serializable {

    public String   oid;
    public String   pid;
    public String   avatar;
    public String   signature;
    public String   mobile;
    public String   lon;
    public String   lat;
    public String   location;
    public String   offer_price;
    public String   appointment_time;
    public String   text;
    public String   subid;
    public int   order_status;

    public DemandsBean() {
        super();
    }

    public DemandsBean(String oid, String pid, String   avatar,String   signature,String mobile, String lon, String lat, String location, String offer_price, String appointment_time, String text, String subid, int order_status) {
        this.oid = oid;
        this.pid = pid;
        this.avatar = avatar;
        this.signature = signature;
        this.mobile = mobile;
        this.lon = lon;
        this.lat = lat;
        this.location = location;
        this.offer_price = offer_price;
        this.appointment_time = appointment_time;
        this.text = text;
        this.subid = subid;
        this.order_status = order_status;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.oid = jsonObject.optString("oid");
        this.pid = jsonObject.optString("pid");
        this.avatar = jsonObject.optString("avatar");
        this.signature = jsonObject.optString("signature");
        this.mobile = jsonObject.optString("mobile");
        this.lon = jsonObject.optString("lon");
        this.lat = jsonObject.optString("lat");
        this.location = jsonObject.optString("location");
        this.offer_price = jsonObject.optString("offer_price");
        this.appointment_time = jsonObject.optString("appointment_time");
        this.text = jsonObject.optString("text");
        this.subid = jsonObject.optString("subid");
        this.order_status = jsonObject.optInt("order_status");


        return ;
    }
    public JSONObject  toJson() throws JSONException{

        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("oid", oid);
        localItemObject.put("pid", pid);
        localItemObject.put("avatar", avatar);
        localItemObject.put("signature", signature);
        localItemObject.put("mobile", mobile);
        localItemObject.put("lon", lon);
        localItemObject.put("lat", lat);
        localItemObject.put("location", location);
        localItemObject.put("offer_price", offer_price);
        localItemObject.put("appointment_time", appointment_time);
        localItemObject.put("text", text);
        localItemObject.put("subid", subid);
        localItemObject.put("order_status", order_status);

        return localItemObject;
    }
}
