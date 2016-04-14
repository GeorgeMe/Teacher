package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface TransactionDetailPresenter {
    void onAcceptOrder(JsonObject jsonObject);
}
