package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.response.EvaluationResponse;
import com.dmd.zsb.entity.EvaluationEntity;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface EvaluationView extends BaseView {

    void navigateToEvaluationDetail(EvaluationEntity data);

    void refreshListData(EvaluationResponse data);

    void addMoreListData(EvaluationResponse data);
}
