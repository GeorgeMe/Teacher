package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.signinResponse;

/**
 * Created by Administrator on 2016/3/10.
 */
public interface SignInView extends BaseView{
    void navigateToHome(signinResponse response);
    void showTip(String msg);
}
