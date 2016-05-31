package com.dmd.zsb.protocol.request;

import com.dmd.zsb.utils.UriHelper;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class homeRequest extends SugarRecord implements Serializable {

    public String   appkey;
    public String version;
    public String   sid;
    public String   uid;
    public int   page;
    public int   rows;
    public String   subid;
    public int  sort;
    public homeRequest() {
        super();
    }

    public homeRequest(String appkey, String version, String sid, String uid, int page, int rows, String subid,int  sort) {
        super();
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.page = page;
        this.rows = rows;
        this.sort = sort;
        this.subid = subid;
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
        this.page = jsonObject.optInt("page");
        this.rows = jsonObject.optInt("rows");
        this.sort = jsonObject.optInt("sort");
        this.subid = jsonObject.optString("subid");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("page", page);
        localItemObject.put("sort", sort);
        localItemObject.put("rows", UriHelper.getInstance().PAGE_LIMIT);
        localItemObject.put("subid", subid);

        return localItemObject;
    }
}
