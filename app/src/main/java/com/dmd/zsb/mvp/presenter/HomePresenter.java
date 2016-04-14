package com.dmd.zsb.mvp.presenter;

import com.dmd.zsb.entity.OrderEntity;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2015/12/15.
 */
public interface HomePresenter {

   // void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh);
    void loadListData(int event,JsonObject data);

}
