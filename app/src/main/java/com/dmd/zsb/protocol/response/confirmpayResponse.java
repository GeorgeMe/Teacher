package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class confirmpayResponse extends SugarRecord implements Serializable {
    public String oid;
    public String order_sn;
    public String order_status;
    public String offer_price;
    public int errno;
    public String msg;

    public confirmpayResponse() {
        super();
    }

    public confirmpayResponse(String oid, String order_sn, String order_status, String offer_price, int errno, String msg) {
        super();
        this.oid = oid;
        this.order_sn = order_sn;
        this.order_status = order_status;
        this.offer_price = offer_price;
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

        this.oid = jsonObject.optString("oid");
        this.order_sn = jsonObject.optString("order_sn");
        this.order_status = jsonObject.optString("order_status");
        this.offer_price = jsonObject.optString("offer_price");

        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);

        localItemObject.put("oid", oid);
        localItemObject.put("order_sn", order_sn);
        localItemObject.put("order_status", order_status);
        localItemObject.put("offer_price", offer_price);

        return localItemObject;
    }
}
