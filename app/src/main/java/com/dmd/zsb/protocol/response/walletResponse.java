package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class walletResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public String   balance;
    public String total_hours;
    public String   buyer_id;
    public String   total_amount;
    public String   bank_card;

    public walletResponse() {
        super();
    }

    public walletResponse(int errno, String msg, String balance, String total_hours, String buyer_id, String total_amount, String bank_card) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.balance = balance;
        this.total_hours = total_hours;
        this.buyer_id = buyer_id;
        this.total_amount = total_amount;
        this.bank_card = bank_card;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");
        this.balance = jsonObject.optString("balance");
        this.total_hours = jsonObject.optString("total_hours");
        this.buyer_id = jsonObject.optString("buyer_id");
        this.total_amount = jsonObject.optString("total_amount");
        this.bank_card = jsonObject.optString("bank_card");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);
        localItemObject.put("balance", balance);
        localItemObject.put("total_hours", total_hours);
        localItemObject.put("buyer_id", buyer_id);
        localItemObject.put("total_amount", total_amount);
        localItemObject.put("bank_card", bank_card);

        return localItemObject;
    }
}
