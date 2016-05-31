package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.homeResponse;
import com.dmd.zsb.protocol.table.UsersBean;

/**
 * Created by George on 2015/12/6.
 */
public interface HomeView extends BaseView{

    void navigateToUserDetail(UsersBean data);

    void refreshListData(homeResponse response);

    void addMoreListData(homeResponse response);

}
