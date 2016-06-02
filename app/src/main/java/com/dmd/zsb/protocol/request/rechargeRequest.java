package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class rechargeRequest extends SugarRecord implements Serializable {

    public String   appkey;
    public String version;
    public String   sid;
    public String   uid;

    public String body;//商品描述
    public String discount;//折扣
    public String voucher;//代金券额度
    public String price;//商品单价
    public String subject;//商品名称

    public rechargeRequest() {
        super();
    }

    public rechargeRequest(String appkey, String version, String sid, String uid, String body, String discount, String voucher, String price, String subject) {
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.body = body;
        this.discount = discount;
        this.voucher = voucher;
        this.price = price;
        this.subject = subject;
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
        this.body = jsonObject.optString("body");
        this.discount = jsonObject.optString("discount");
        this.voucher = jsonObject.optString("voucher");
        this.price = jsonObject.optString("price");
        this.subject = jsonObject.optString("subject");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("body", body);
        localItemObject.put("discount", discount);
        localItemObject.put("voucher", voucher);
        localItemObject.put("price", price);
        localItemObject.put("subject", subject);

        return localItemObject;
    }
}
