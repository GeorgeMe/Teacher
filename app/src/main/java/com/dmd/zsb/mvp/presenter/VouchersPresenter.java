package com.dmd.zsb.mvp.presenter;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface VouchersPresenter {
    void onVouchers(int event_tag,JSONObject jsonObject);
}
