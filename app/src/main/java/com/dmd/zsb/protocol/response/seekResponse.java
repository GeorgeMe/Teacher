package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.UsersBean;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class seekResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public int total_count;
    public List<UsersBean> users;

    public seekResponse() {
        super();
    }

    public seekResponse(int errno, String msg, int total_count, List<UsersBean> users) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.total_count = total_count;
        this.users = users;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.total_count = jsonObject.optInt("total_count");
        this.msg = jsonObject.optString("msg");

        subItemArray=jsonObject.optJSONArray("users");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                UsersBean subItem = new UsersBean();
                subItem.fromJson(subItemObject);
                this.users.add(subItem);
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
        for(int i =0; i< users.size(); i++)
        {
            UsersBean itemData =users.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("users", itemJSONArray);
        return localItemObject;
    }
}
