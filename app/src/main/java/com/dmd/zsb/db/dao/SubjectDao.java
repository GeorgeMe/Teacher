package com.dmd.zsb.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dmd.tutor.utils.StringUtils;
import com.dmd.zsb.common.TableConstant;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.entity.GradeEntity;
import com.dmd.zsb.entity.SubjectEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/17.
 */
public class SubjectDao{
    private String TAG="SubjectDao";
    public ZSBDataBase zsbDataBase;
    public SQLiteDatabase sqlDB;
    public SubjectDao(ZSBDataBase zsbDataBase) {
        this.zsbDataBase = zsbDataBase;
        sqlDB = zsbDataBase.getSqlDB();
    }


    /**
     * 判断数据库是否为空
     *
     * @Title: isEmpty
     * @Description: TODO
     * @return
     * @return: boolean
     */
    public boolean isEmpty() {
        return zsbDataBase.tableIsEmpty(TableConstant.T_SUBJECT);
    }

    /**
     * 删除所有数据
     *
     * @Title: deleteAllInfo
     * @Description: TODO
     * @return
     * @return: int
     */
    public int deleteAllInfo() {
        return sqlDB.delete(TableConstant.T_SUBJECT, null, null);
    }
    /**
     * 年级保存到数据库中
     *
     * @Title: saveGrade
     * @Description: TODO
     * @param subjectEntities
     *            是否是暂存数据
     * @return: void
     */
    public void saveSubject(List<SubjectEntity> subjectEntities) {
        Log.i(TAG, "年级保存");
        sqlDB.beginTransaction(); // 手动设置开始事务
        ContentValues contentValues;
        // 数据插入操作循环
        for (SubjectEntity subjectEntity : subjectEntities) {
            contentValues = new ContentValues();
            contentValues.put("sub_id", StringUtils.getString(subjectEntity.getSub_id()));
            contentValues.put("sub_img", StringUtils.getString(subjectEntity.getSub_img()));
            contentValues.put("sub_name", StringUtils.getString(subjectEntity.getSub_name()));
            contentValues.put("grade_id", StringUtils.getString(subjectEntity.getGrade_id()));
            sqlDB.insert(TableConstant.T_SUBJECT, null, contentValues);
        }
        sqlDB.setTransactionSuccessful();
        sqlDB.endTransaction();
        Log.i(TAG, "年级保存完毕");
    }
    public List<SubjectEntity> getGrades() {
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<SubjectEntity>();
        Cursor cursor = sqlDB.query(TableConstant.T_GRADE, null,null, null, null, null, null);
        while (cursor.moveToNext()) {
            SubjectEntity subjectEntity = new SubjectEntity();
            subjectEntity.setSub_id(cursor.getString(cursor.getColumnIndex("sub_id")));
            subjectEntity.setSub_img(cursor.getString(cursor.getColumnIndex("sub_img")));
            subjectEntity.setSub_name(cursor.getString(cursor.getColumnIndex("sub_name")));
            subjectEntity.setGrade_id(cursor.getString(cursor.getColumnIndex("grade_id")));
            subjectEntities.add(subjectEntity);
        }
        return subjectEntities;
    }

    public ArrayList<SubjectEntity> getGrades(String grade_id) {
        ArrayList<SubjectEntity> subjectEntities = new ArrayList<SubjectEntity>();
        String sql = "grade_id = '"+ grade_id + "' order by sub_id";
        Cursor cursor = sqlDB.query(TableConstant.T_SUBJECT, null,sql, null, null, null, null);
        while (cursor.moveToNext()) {
            SubjectEntity subjectEntity = new SubjectEntity();
            subjectEntity.setSub_id(cursor.getString(cursor.getColumnIndex("sub_id")));
            subjectEntity.setSub_img(cursor.getString(cursor.getColumnIndex("sub_img")));
            subjectEntity.setSub_name(cursor.getString(cursor.getColumnIndex("sub_name")));
            subjectEntity.setGrade_id(cursor.getString(cursor.getColumnIndex("grade_id")));
            subjectEntities.add(subjectEntity);
        }
        cursor.close();
        return subjectEntities;
    }

    public SubjectEntity getSubjectEntity(String grade_id,String sub_name){
        SubjectEntity subjectEntity = new SubjectEntity();
        String sql = "grade_id = '"+ grade_id +"' and "+"sub_name = '"+ sub_name+"'";
        Cursor cursor = sqlDB.query(TableConstant.T_SUBJECT, null,sql, null, null, null, null);
        while (cursor.moveToNext()) {
            subjectEntity.setSub_id(cursor.getString(cursor.getColumnIndex("sub_id")));
            subjectEntity.setSub_img(cursor.getString(cursor.getColumnIndex("sub_img")));
            subjectEntity.setSub_name(cursor.getString(cursor.getColumnIndex("sub_name")));
            subjectEntity.setGrade_id(cursor.getString(cursor.getColumnIndex("grade_id")));
        }
        cursor.close();
        return subjectEntity;
    }
}
