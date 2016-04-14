package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.GradeEntity;
import com.dmd.zsb.entity.SubjectEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
public class SplashResponse {

    private List<GradeEntity> gradeList;
    private List<SubjectEntity> subjectList;

    public List<SubjectEntity> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectEntity> subjectList) {
        this.subjectList = subjectList;
    }

    public List<GradeEntity> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeEntity> gradeList) {
        this.gradeList = gradeList;
    }
}
