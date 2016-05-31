package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class signinRequest extends SugarRecord implements Serializable
{
     public String   appkey;
     public String   client_type;
     public String   mobile;
     public String   role;
     public double   lat;
     public double   lon;
     public String location;
     public String version;
     public String   password;

     public signinRequest() {
          super();
     }

     public signinRequest(String appkey, String client_type, String mobile, String role, double lat, double lon, String location, String version, String password) {
          super();
          this.appkey = appkey;
          this.client_type = client_type;
          this.mobile = mobile;
          this.role = role;
          this.lat = lat;
          this.lon = lon;
          this.location = location;
          this.version = version;
          this.password = password;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          this.appkey = jsonObject.optString("appkey");
          this.client_type = jsonObject.optString("client_type");
          this.mobile = jsonObject.optString("mobile");
          this.role = jsonObject.optString("role");
          this.lon = jsonObject.optDouble("lon");
          this.lat = jsonObject.optDouble("lat");
          this.location = jsonObject.optString("location");
          this.version = jsonObject.optString("version");
          this.password = jsonObject.optString("password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("appkey", appkey);
          localItemObject.put("client_type", client_type);
          localItemObject.put("mobile", mobile);
          localItemObject.put("role", role);
          localItemObject.put("lat", lat);
          localItemObject.put("lon", lon);
          localItemObject.put("location", location);
          localItemObject.put("version", version);
          localItemObject.put("password", password);
          return localItemObject;
     }

}
