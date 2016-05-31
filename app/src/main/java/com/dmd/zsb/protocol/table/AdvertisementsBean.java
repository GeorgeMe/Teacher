package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class AdvertisementsBean extends SugarRecord implements Serializable {

    public String img_url;
    public String link;
    public String title;
    public String description;

    public AdvertisementsBean() {
        super();
    }

    public AdvertisementsBean(String img_url, String link, String title, String description) {
        super();
        this.img_url = img_url;
        this.link = link;
        this.title = title;
        this.description = description;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.img_url = jsonObject.optString("img_url");
        this.link = jsonObject.optString("link");
        this.title = jsonObject.optString("title");
        this.description = jsonObject.optString("description");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("img_url", img_url);
        localItemObject.put("link", link);
        localItemObject.put("title", title);
        localItemObject.put("description", description);
        return localItemObject;
    }
}
