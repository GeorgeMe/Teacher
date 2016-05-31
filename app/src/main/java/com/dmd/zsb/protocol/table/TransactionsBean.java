package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class TransactionsBean extends SugarRecord implements Serializable {

    public String body;
    public String subject;
    public String gmt_payment;
    public String total_fee;
    public String out_trade_no;
    public String trade_status;

    public TransactionsBean() {
        super();
    }

    public TransactionsBean(String body, String subject, String gmt_payment, String total_fee, String out_trade_no, String trade_status) {
        super();
        this.body = body;
        this.subject = subject;
        this.gmt_payment = gmt_payment;
        this.total_fee = total_fee;
        this.out_trade_no = out_trade_no;
        this.trade_status = trade_status;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.body = jsonObject.optString("body");
        this.subject = jsonObject.optString("subject");
        this.gmt_payment = jsonObject.optString("gmt_payment");
        this.total_fee = jsonObject.optString("total_fee");
        this.out_trade_no = jsonObject.optString("out_trade_no");
        this.trade_status = jsonObject.optString("trade_status");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("body", body);
        localItemObject.put("subject", subject);
        localItemObject.put("gmt_payment", gmt_payment);
        localItemObject.put("total_fee", total_fee);
        localItemObject.put("out_trade_no", out_trade_no);
        localItemObject.put("trade_status", trade_status);

        return localItemObject;
    }
}
