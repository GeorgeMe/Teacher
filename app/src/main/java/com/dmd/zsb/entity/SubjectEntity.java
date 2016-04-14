package com.dmd.zsb.entity;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SubjectEntity{


    private String sub_id;

    private String sub_img;

    private String sub_name;

    private String grade_id;

    public SubjectEntity() {
    }

    public SubjectEntity(String sub_id, String sub_img, String sub_name, String grade_id) {
        this.sub_id = sub_id;
        this.sub_img = sub_img;
        this.sub_name = sub_name;
        this.grade_id = grade_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_img() {
        return sub_img;
    }

    public void setSub_img(String sub_img) {
        this.sub_img = sub_img;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }
}
