package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.feedbackResponse;

/**
 * Created by Administrator on 2016/3/25.
 */
public interface FeedbackView  extends BaseView{
    void navigateToSetting(feedbackResponse data);
    void showTip(String msg);
}
