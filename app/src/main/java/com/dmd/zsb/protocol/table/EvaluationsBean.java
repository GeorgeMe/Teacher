package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class EvaluationsBean  extends SugarRecord implements Serializable {

    public String teacher;
    public String parent;
    public String appointed_time;
    public String offer_price;
    public String subject;
    public String text;
    public String content;
    public String rank;
    public String status;
    public EvaluationsBean() {
        super();
    }

    public EvaluationsBean(String teacher, String parent, String appointed_time, String offer_price, String subject, String text, String content, String rank,String status) {
        this.teacher = teacher;
        this.parent = parent;
        this.appointed_time = appointed_time;
        this.offer_price = offer_price;
        this.subject = subject;
        this.text = text;
        this.content = content;
        this.rank = rank;
        this.status = status;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.teacher = jsonObject.optString("teacher");
        this.parent = jsonObject.optString("parent");
        this.appointed_time = jsonObject.optString("appointed_time");
        this.offer_price = jsonObject.optString("offer_price");
        this.subject = jsonObject.optString("subject");
        this.text = jsonObject.optString("text");
        this.content = jsonObject.optString("content");
        this.rank = jsonObject.optString("rank");
        this.status = jsonObject.optString("status");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("teacher", teacher);
        localItemObject.put("parent", parent);
        localItemObject.put("appointed_time", appointed_time);
        localItemObject.put("offer_price", offer_price);
        localItemObject.put("subject", subject);
        localItemObject.put("text", text);
        localItemObject.put("content", content);
        localItemObject.put("rank", rank);
        localItemObject.put("status", status);
        return localItemObject;
    }
}
