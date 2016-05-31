package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.walletResponse;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface WalletView  extends BaseView{
    void setView(walletResponse response);
    void showTip(String msg);
}
