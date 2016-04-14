package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.EvaluationEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class EvaluationResponse {
    private int total_page;
    private List<EvaluationEntity> evaluationEntities;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<EvaluationEntity> getEvaluationEntities() {
        return evaluationEntities;
    }

    public void setEvaluationEntities(List<EvaluationEntity> evaluationEntities) {
        this.evaluationEntities = evaluationEntities;
    }
}
