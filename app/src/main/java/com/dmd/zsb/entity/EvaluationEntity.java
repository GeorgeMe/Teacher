package com.dmd.zsb.entity;

/**
 * Created by Administrator on 2016/3/29.
 */
public class EvaluationEntity {

    private String img_header;//头像
    private String name;//姓名
    private String type;//职业类型
    private String sex;//性别
    private String appointed_time;//约定时间
    private String charging;//计费
    private String curriculum;//课程
    private String note;//评论内容

    private String comment_level;//评论等级

    public String getImg_header() {
        return img_header;
    }

    public void setImg_header(String img_header) {
        this.img_header = img_header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAppointed_time() {
        return appointed_time;
    }

    public void setAppointed_time(String appointed_time) {
        this.appointed_time = appointed_time;
    }

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment_level() {
        return comment_level;
    }

    public void setComment_level(String comment_level) {
        this.comment_level = comment_level;
    }
}
