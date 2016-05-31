package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.OrdersBean;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class orderResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public int total_count;
    public List<OrdersBean> orders;

    public orderResponse() {
        super();
    }

    public orderResponse(int errno, String msg, int total_count, List<OrdersBean> orders) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.total_count = total_count;
        this.orders = orders;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.total_count = jsonObject.optInt("total_count");
        this.msg = jsonObject.optString("msg");
        subItemArray=jsonObject.optJSONArray("demands");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                OrdersBean subItem = new OrdersBean();
                subItem.fromJson(subItemObject);
                this.orders.add(subItem);
            }
        }
        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("total_count", total_count);
        localItemObject.put("msg", msg);
        for(int i =0; i< orders.size(); i++)
        {
            OrdersBean itemData =orders.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("orders", itemJSONArray);

        return localItemObject;
    }
}
