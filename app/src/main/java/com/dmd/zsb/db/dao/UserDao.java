package com.dmd.zsb.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.dmd.zsb.db.ZSBDataBase;


/**
 * Created by Administrator on 2016/3/17.
 */
public class UserDao {
    public ZSBDataBase zsbDataBase;
    public SQLiteDatabase sqlDB;
    public UserDao(ZSBDataBase zsbDataBase) {
        this.zsbDataBase = zsbDataBase;
        sqlDB = zsbDataBase.getSqlDB();
    }
}
