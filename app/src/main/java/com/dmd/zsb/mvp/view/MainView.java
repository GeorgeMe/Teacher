package com.dmd.zsb.mvp.view;

import android.view.View;

public interface MainView  extends BaseView{

    void initTabView();

    View getIndicatorView(String tab);

    void setHomeText(boolean isSelected);

    void setMessageText(boolean isSelected);

  //  void setSeekText(boolean isSelected);

    void setMineText(boolean isSelected);

}
