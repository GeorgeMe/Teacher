package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.signupResponse;

/**
 * Created by Administrator on 2016/3/10.
 */
public interface SignUpView  extends BaseView{
    void navigateToHome(signupResponse response);
    void showTip(String msg);
}
