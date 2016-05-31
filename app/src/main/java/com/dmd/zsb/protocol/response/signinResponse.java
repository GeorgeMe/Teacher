package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class signinResponse extends SugarRecord implements Serializable
{
     public String   sid;
     public String   uid;
     public int errno;
     public String msg;

     public signinResponse() {
          super();
     }

     public signinResponse(String sid, String uid, int errno, String msg) {
          super();
          this.sid = sid;
          this.uid = uid;
          this.errno = errno;
          this.msg = msg;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.sid = jsonObject.optString("sid");
          this.uid = jsonObject.optString("uid");
          this.errno = jsonObject.optInt("errno");
          this.msg = jsonObject.optString("msg");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("sid", sid);
          localItemObject.put("uid", uid);
          localItemObject.put("errno", errno);
          localItemObject.put("msg", msg);
          return localItemObject;
     }

}
