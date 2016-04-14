package com.dmd.zsb.mvp.listeners;

import com.google.gson.JsonObject;

public interface CommonListInteractor {
    void getCommonListData(int event, JsonObject gson);
}
