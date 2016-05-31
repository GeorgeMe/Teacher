package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.evaluationResponse;
import com.dmd.zsb.protocol.table.EvaluationsBean;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface EvaluationView extends BaseView {

    void navigateToEvaluationDetail(EvaluationsBean data);

    void refreshListData(evaluationResponse response);

    void addMoreListData(evaluationResponse response);
}
