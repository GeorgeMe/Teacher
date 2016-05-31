
package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LOCATION  extends SugarRecord implements Serializable
{
     public double   lon;
     public String   name;
     public double   lat;

     public LOCATION() {
          super();
     }

     public LOCATION(double lon, String name, double lat) {
          super();
          this.lon = lon;
          this.name = name;
          this.lat = lat;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          this.lon = jsonObject.optDouble("lon");

          this.name = jsonObject.optString("name");

          this.lat = jsonObject.optDouble("lat");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("lon", lon);
          localItemObject.put("name", name);
          localItemObject.put("lat", lat);
          return localItemObject;
     }

}
