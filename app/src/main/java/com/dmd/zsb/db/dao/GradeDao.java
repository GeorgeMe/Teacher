package com.dmd.zsb.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dmd.tutor.utils.StringUtils;
import com.dmd.zsb.common.TableConstant;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.entity.GradeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
public class GradeDao {
    private String TAG="GradeDao";
    public ZSBDataBase zsbDataBase;
    public SQLiteDatabase sqlDB;
    public GradeDao(ZSBDataBase zsbDataBase) {
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
        return zsbDataBase.tableIsEmpty(TableConstant.T_GRADE);
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
        return sqlDB.delete(TableConstant.T_GRADE, null, null);
    }

    /**
     * 删除暂存数据
     *
     * @Title: deleteTempSaveInfo
     * @Description: TODO
     * @return
     * @return: int
     */
    public int deleteTempSaveInfo() {
        return sqlDB.delete(TableConstant.T_GRADE, null, null);
    }

    /**
     * 年级保存到数据库中
     *
     * @Title: saveGrade
     * @Description: TODO
     * @param gradeEntities
     *            是否是暂存数据
     * @return: void
     */
    public void saveGrade(List<GradeEntity> gradeEntities) {
        Log.i(TAG, "年级保存");
        sqlDB.beginTransaction(); // 手动设置开始事务
        ContentValues contentValues;
        // 数据插入操作循环
        for (GradeEntity gradeEntity : gradeEntities) {
            contentValues = new ContentValues();
            contentValues.put("grade_id", StringUtils.getString(gradeEntity.getGrade_id()));
            contentValues.put("grade_name", StringUtils.getString(gradeEntity.getGrade_name()));
            sqlDB.insert(TableConstant.T_GRADE, null, contentValues);
        }
        sqlDB.setTransactionSuccessful();
        sqlDB.endTransaction();
        Log.i(TAG, "年级保存完毕");
    }

    public List<GradeEntity> getGrades() {
        ArrayList<GradeEntity> gradeEntities = new ArrayList<GradeEntity>();
        Cursor cursor = sqlDB.query(TableConstant.T_GRADE, null,null, null, null, null, null);
        while (cursor.moveToNext()) {
            GradeEntity gradeEntity = new GradeEntity();
            gradeEntity.setGrade_id(cursor.getString(cursor.getColumnIndex("grade_id")));
            gradeEntity.setGrade_name(cursor.getString(cursor.getColumnIndex("grade_name")));
            gradeEntities.add(gradeEntity);
        }
        cursor.close();
        return gradeEntities;
    }

    public void updateGrade(GradeEntity gradeEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("grade_id", StringUtils.getString(gradeEntity.getGrade_id()));
        contentValues.put("grade_name", StringUtils.getString(gradeEntity.getGrade_name()));
        String whereClause = "grade_id=? and grade_name=? ";
        String[] whereArgs = new String[]{
                StringUtils.getString(gradeEntity.getGrade_id()),
                StringUtils.getString(gradeEntity.getGrade_name())
        };
        sqlDB.update(TableConstant.T_GRADE, contentValues, whereClause,whereArgs);
    }

    public void deleteGrade(GradeEntity gradeEntity) {
        String whereClause = "grade_id=? and grade_name=? ";
        String[] whereArgs = new String[] {
                StringUtils.getString(gradeEntity.getGrade_id()),
                StringUtils.getString(gradeEntity.getGrade_name())
        };
        sqlDB.delete(TableConstant.T_GRADE, whereClause, whereArgs);
    }

    public void deleteGrade(ArrayList<GradeEntity> gradeEntities) {
        sqlDB.beginTransaction(); // 手动设置开始事务
        for (GradeEntity gradeEntity : gradeEntities) {
            deleteGrade(gradeEntity);
        }
        sqlDB.setTransactionSuccessful();
        sqlDB.endTransaction();
    }
}
