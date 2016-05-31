package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import java.io.Serializable;

public class SESSION  extends SugarRecord implements Serializable
{
    public String   appkey;
    public String version;
     public int uid ;
     public String sid;

    public SESSION() {
        super();
    }

    public SESSION(String appkey, String version, int uid, String sid) {
        super();
        this.appkey = appkey;
        this.version = version;
        this.uid = uid;
        this.sid = sid;
    }

    public static SESSION instance;
     public static SESSION getInstance() {
         if (instance == null) {
             instance = new SESSION();
         }
         return instance;
     }

}
