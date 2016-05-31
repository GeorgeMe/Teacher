package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class OrdersBean  extends SugarRecord implements Serializable {

    public String oid;
    public String order_sn;
    public String offer_price;
    public String appointment_time;
    public int order_status;
    public String location;
    public String created_at;
    public String lon;
    public String lat;
    public String text;
    public String subject;
    public String receiver_id;

    public OrdersBean() {
        super();
    }

    public OrdersBean(String oid, String order_sn, String offer_price, String appointment_time, int order_status, String location, String created_at, String lon, String lat, String text,String subject,String receiver_id) {
        this.oid = oid;
        this.order_sn = order_sn;
        this.offer_price = offer_price;
        this.appointment_time = appointment_time;
        this.order_status = order_status;
        this.location = location;
        this.created_at = created_at;
        this.lon = lon;
        this.lat = lat;
        this.text = text;
        this.subject = subject;
        this.receiver_id = receiver_id;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.oid = jsonObject.optString("oid");
        this.order_sn = jsonObject.optString("order_sn");
        this.offer_price = jsonObject.optString("offer_price");
        this.appointment_time = jsonObject.optString("appointment_time");
        this.order_status = jsonObject.optInt("order_status");
        this.location = jsonObject.optString("location");
        this.created_at = jsonObject.optString("created_at");
        this.lon = jsonObject.optString("lon");
        this.lat = jsonObject.optString("lat");
        this.text = jsonObject.optString("text");
        this.subject = jsonObject.optString("subject");
        this.receiver_id = jsonObject.optString("receiver_id");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("oid", oid);
        localItemObject.put("order_sn", order_sn);
        localItemObject.put("offer_price", offer_price);
        localItemObject.put("appointment_time", appointment_time);
        localItemObject.put("order_status", order_status);
        localItemObject.put("location", location);
        localItemObject.put("created_at", created_at);
        localItemObject.put("lon", lon);
        localItemObject.put("lat", lat);
        localItemObject.put("text", text);
        localItemObject.put("subject", subject);
        localItemObject.put("receiver_id", receiver_id);

        return localItemObject;
    }

}
