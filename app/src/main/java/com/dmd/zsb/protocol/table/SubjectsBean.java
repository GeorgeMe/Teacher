package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class SubjectsBean extends SugarRecord implements Serializable {

    public String sub_id;
    public String sub_img;
    public String sub_name;
    public String grade_id;

    public SubjectsBean() {
        super();
    }

    public SubjectsBean(String sub_id, String sub_img, String sub_name, String grade_id) {
        super();
        this.sub_id = sub_id;
        this.sub_img = sub_img;
        this.sub_name = sub_name;
        this.grade_id = grade_id;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.sub_id = jsonObject.optString("sub_id");
        this.sub_img = jsonObject.optString("sub_img");
        this.sub_name = jsonObject.optString("sub_name");
        this.grade_id = jsonObject.optString("grade_id");
        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("sub_id", sub_id);
        localItemObject.put("sub_img", sub_img);
        localItemObject.put("sub_name", sub_name);
        localItemObject.put("grade_id", grade_id);
        return localItemObject;
    }
}
