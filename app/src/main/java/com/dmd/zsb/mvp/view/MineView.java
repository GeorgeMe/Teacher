package com.dmd.zsb.mvp.view;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface MineView {
    void setMineView(JsonObject jsonObject);
    void showTip(String msg);
}
