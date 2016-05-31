package com.dmd.zsb.mvp.presenter;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/25.
 */
public interface SettingPresenter {
    void uploadAvatar(int event,JSONObject jsonObject);
    void onSignOut(int event,JSONObject jsonObject);
}
