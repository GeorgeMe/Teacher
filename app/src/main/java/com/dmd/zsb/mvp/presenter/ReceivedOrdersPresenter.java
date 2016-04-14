package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface ReceivedOrdersPresenter {
    void onReceivedOrders(int event,JsonObject jsonObject);
}
