package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class attestationRequest extends SugarRecord implements Serializable {

    public String   appkey;
    public String version;
    public String   sid;
    public String   uid;
    public String   fileMime;
    public String   attestation_name;
    public String   attestation_type;
    public String   attestation_number;
    public String   attestation_img;


    public attestationRequest() {
        super();
    }

    public attestationRequest(String appkey, String version, String sid, String uid, String  fileMime,String attestation_name, String attestation_type, String attestation_number, String attestation_img) {
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.fileMime = fileMime;
        this.attestation_name = attestation_name;
        this.attestation_type = attestation_type;
        this.attestation_number = attestation_number;
        this.attestation_img = attestation_img;
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
        this.fileMime = jsonObject.optString("fileMime");
        this.attestation_name = jsonObject.optString("attestation_name");
        this.attestation_type = jsonObject.optString("attestation_type");
        this.attestation_number = jsonObject.optString("attestation_number");
        this.attestation_img = jsonObject.optString("attestation_img");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("fileMime", fileMime);
        localItemObject.put("attestation_name", attestation_name);
        localItemObject.put("attestation_type", attestation_type);
        localItemObject.put("attestation_number", attestation_number);
        localItemObject.put("attestation_img", attestation_img);

        return localItemObject;
    }
}
