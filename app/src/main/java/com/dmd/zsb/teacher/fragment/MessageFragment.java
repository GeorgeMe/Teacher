package com.dmd.zsb.teacher.fragment;

import android.view.View;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseFragment;


public class MessageFragment extends BaseFragment{
    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }
}
