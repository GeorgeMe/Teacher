package com.dmd.zsb.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.dmd.zsb.common.TableConstant;

public class ZSBDataBase {
	private String DB_NAME = "zsb.db";
	private int DB_VERSION = 1;
	private SQLiteDatabase sqlDB;
	public static ZSBDataBase zsbDataBase;

	/**
	 * 初始化数据库
	 * 
	 * @param context
	 */
	private ZSBDataBase(Context context) {
		if (sqlDB == null || (!sqlDB.isOpen())) {
			sqlDB = new DataBaseHelper(context).getWritableDatabase();
		}

	}

	public static ZSBDataBase getInstance(Context context) {
		if (zsbDataBase == null) {
			zsbDataBase = new ZSBDataBase(context);
		}
		return zsbDataBase;
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (sqlDB != null) {
			sqlDB.close();
			sqlDB = null;
		}
		if (zsbDataBase != null) {
			zsbDataBase = null;
		}
	}

	public SQLiteDatabase getSqlDB() {
		return sqlDB;
	}

	public void setSqlDB(SQLiteDatabase sqlDB) {
		this.sqlDB = sqlDB;
	}

	/**
	 * 查询表是否为空
	 * 
	 * @param tablename
	 *            tableAdviceCategory
	 * @return
	 */
	public boolean tableIsEmpty(String tablename) {
		// 返回true为空，
		boolean flag = false;
		Cursor cursor = sqlDB.query(tablename, null, null, null, null, null,null);
		int x = cursor.getCount();
		cursor.close();
		if (x == 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 数据库帮助类 表的创建 和数据库的更新，
	 * 
	 * @author chenliang
	 * @date 2012-12-24
	 */
	public class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		public DataBaseHelper(Context context) {
			this(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String CommunityPatientSql = "CREATE TABLE " + TableConstant.T_GRADE+ " (grade_id varchar(255) PRIMARY KEY," + "grade_name varchar(255))";
			db.execSQL(CommunityPatientSql);

			String drugSql = "CREATE TABLE " + TableConstant.T_SUBJECT
					+ " (sub_id varchar(30) PRIMARY KEY," + "sub_img varchar(10),"
					+ "sub_name varchar(10)," + "grade_id varchar(30))";
			db.execSQL(drugSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			if (newVersion > oldVersion) {
				// TODO 更新数据库版本的时候 要保存以前的数据的操作
				db.execSQL("drop table if exists " + TableConstant.T_GRADE);
				db.execSQL("drop table if exists " + TableConstant.T_SUBJECT);
			}
			onCreate(db);
		}

	}
}
