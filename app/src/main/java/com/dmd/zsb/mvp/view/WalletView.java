package com.dmd.zsb.mvp.view;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface WalletView {
    void setView(JsonObject jsonObject);
    void showTip(String msg);
}
