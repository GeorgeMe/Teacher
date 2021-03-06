package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.AdvertisementsBean;
import com.dmd.zsb.protocol.table.DemandsBean;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class homeResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public int total_count;
    public List<DemandsBean> demands;
    public List<AdvertisementsBean> advertisements;

    public homeResponse() {
        super();
    }

    public homeResponse(int errno, String msg, int total_count, List<DemandsBean> demands, List<AdvertisementsBean> advertisements) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.total_count = total_count;
        this.demands = demands;
        this.advertisements = advertisements;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        JSONArray subItemArray2;
        JSONArray subItemArray3;

        this.errno = jsonObject.optInt("errno");
        this.total_count = jsonObject.optInt("total_count");
        this.msg = jsonObject.optString("msg");
        subItemArray=jsonObject.optJSONArray("demands");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                DemandsBean subItem = new DemandsBean();
                subItem.fromJson(subItemObject);
                this.demands.add(subItem);
            }
        }
        subItemArray3=jsonObject.optJSONArray("advertisements");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray3.length(); i++) {
                JSONObject subItemObject = subItemArray3.getJSONObject(i);
                AdvertisementsBean subItem = new AdvertisementsBean();
                subItem.fromJson(subItemObject);
                this.advertisements.add(subItem);
            }
        }
        return ;
    }


    public JSONObject  toJson() throws JSONException{

        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        JSONArray itemJSONArray3 = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("total_count", total_count);
        localItemObject.put("msg", msg);

        for(int i =0; i< demands.size(); i++)
        {
            DemandsBean itemData =demands.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("demands", itemJSONArray);

        for(int i =0; i< advertisements.size(); i++)
        {
            AdvertisementsBean itemData =advertisements.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray3.put(itemJSONObject);
        }
        localItemObject.put("advertisements", itemJSONArray3);
        return localItemObject;
    }
}
