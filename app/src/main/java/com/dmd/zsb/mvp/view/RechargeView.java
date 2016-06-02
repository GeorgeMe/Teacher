package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.rechargeResponse;

/**
 * Created by Administrator on 2016/6/1.
 */
public interface RechargeView extends BaseView{
    void onRechargeView(rechargeResponse response);
}
