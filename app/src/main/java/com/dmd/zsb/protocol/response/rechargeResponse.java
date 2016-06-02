package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.Recharge;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class rechargeResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public Recharge recharge;
    public rechargeResponse() {
        super();
    }

    public rechargeResponse(int errno, String msg,Recharge recharge) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.recharge=recharge;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");
        Recharge recharge=new Recharge();

        recharge.fromJson(jsonObject.optJSONObject("recharge"));
        this.recharge=recharge;

        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);
        if (recharge!=null){
            localItemObject.put("recharge",recharge.toJson());
        }
        return localItemObject;
    }
}
