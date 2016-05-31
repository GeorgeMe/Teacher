package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.mineResponse;

/**
 * Created by Administrator on 2016/4/3.
 */
public interface MineView  extends BaseView{
    void setView(mineResponse response);
    void showTip(String msg);
}
