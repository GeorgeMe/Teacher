package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/6.
 */
public interface TransactionPresentter {
    void onTransaction(int event_tag, JsonObject jsonObject);
}
