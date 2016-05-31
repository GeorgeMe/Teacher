package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class mineResponse extends SugarRecord implements Serializable
{
     public String   user_id;
     public String   user_avatar;
     public String   user_signature;
     public String   user_large_img;
     public int errno;
     public String msg;

     public mineResponse() {
          super();
     }

     public mineResponse(String user_id, String user_avatar, String user_signature, String user_large_img, int errno, String msg) {
          this.user_id = user_id;
          this.user_avatar = user_avatar;
          this.user_signature = user_signature;
          this.user_large_img = user_large_img;
          this.errno = errno;
          this.msg = msg;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.user_id = jsonObject.optString("user_id");
          this.user_avatar = jsonObject.optString("user_avatar");
          this.user_signature = jsonObject.optString("user_signature");
          this.user_large_img = jsonObject.optString("user_large_img");
          this.errno = jsonObject.optInt("errno");
          this.msg = jsonObject.optString("msg");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("user_id", user_id);
          localItemObject.put("user_avatar", user_avatar);
          localItemObject.put("user_signature", user_signature);
          localItemObject.put("user_large_img", user_large_img);
          localItemObject.put("errno", errno);
          localItemObject.put("msg", msg);
          return localItemObject;
     }

}
