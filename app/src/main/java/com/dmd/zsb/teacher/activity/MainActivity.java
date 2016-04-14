package com.dmd.zsb.teacher.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.mvp.presenter.impl.MainViewImpl;
import com.dmd.zsb.mvp.view.MainView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.teacher.fragment.HomeFragment;
import com.dmd.zsb.teacher.fragment.MessageFragment;
import com.dmd.zsb.teacher.fragment.MineFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, TabHost.OnTabChangeListener {
    @Bind(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private static long DOUBLE_CLICK_TIME = 0L;

    public static final String TAB_HOME = "home";
    public static final String TAB_MESSAGE = "message";
    public static final String TAB_MINE = "mine";

    private TextView mHomeTab,mMessageTab,mMineTab,mUnread;
    private Drawable mHomePressed,mHomeNormal,mMessagePressed,mMessageNormal,mMinePressed,mMineNormal;

    private MainViewImpl mainView;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                showToast(getString(R.string.double_click_exit));
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                getBaseApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {
/*        if (eventCenter.getEventCode()== Constants.EVENT_RECOMMEND_COURSES_HOME){
            SubjectEntity entity=(SubjectEntity) eventCenter.getData();
            XmlDB.getInstance(mContext).saveKey("subid",entity.getSub_id());
            //showToast(entity.getSubid()););
            tabhost.onTabChanged(TAB_SEEK);
            tabhost.setCurrentTabByTag(TAB_SEEK);
        }*/
    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(this,R.id.realtabcontent);
    }

    @Override
    protected void initViewsAndEvents() {
        mainView=new MainViewImpl(this,this);
        mainView.initialized();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void initTabView() {
        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabhost.getTabWidget().setDividerDrawable(null);

        View indicator = getIndicatorView(TAB_HOME);
        tabhost.addTab(tabhost.newTabSpec(TAB_HOME).setIndicator(indicator),HomeFragment.class, null);

        indicator = getIndicatorView(TAB_MESSAGE);
        tabhost.addTab(tabhost.newTabSpec(TAB_MESSAGE).setIndicator(indicator), MessageFragment.class, null);

        indicator = getIndicatorView(TAB_MINE);
        tabhost.addTab(tabhost.newTabSpec(TAB_MINE).setIndicator(indicator), MineFragment.class, null);

        mUnread = (TextView) findViewById(R.id.unread);

        tabhost.setOnTabChangedListener(this);
        this.onTabChanged(TAB_HOME);
    }

    @Override
    public View getIndicatorView(String tab) {
        View tabView = View.inflate(this, R.layout.tutor_tab_item, null);
        TextView indicator = (TextView) tabView.findViewById(R.id.tab_text);
        Drawable drawable;

        if (tab.equals(TAB_HOME)) {
            indicator.setText("主页");
            drawable = getResources().getDrawable(R.drawable.nav_home_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mHomeTab = indicator;
        } else if (tab.equals(TAB_MESSAGE)) {
            indicator.setText("消息");
            drawable = getResources().getDrawable(R.drawable.nav_message_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMessageTab = indicator;
        } else if (tab.equals(TAB_MINE)) {
            indicator.setText("我的");
            drawable = getResources().getDrawable(R.drawable.nav_message_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMineTab = indicator;
        }
        return tabView;
    }

    @Override
    public void setHomeText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mHomeTab.setTextColor(getResources().getColor(R.color.tab_pressed_color));
            if (mHomePressed == null) {
                mHomePressed = getResources().getDrawable(R.drawable.nav_home_pressed);
            }
            drawable = mHomePressed;
        } else {
            mHomeTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (mHomeNormal == null) {
                mHomeNormal = getResources().getDrawable(R.drawable.nav_home_normal);
            }
            drawable = mHomeNormal;
        }
        if (null != drawable) {// 此处出现过NP问题，加保护
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mHomeTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void setMessageText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMessageTab.setTextColor(getResources().getColor(R.color.tab_pressed_color));
            if (mMessagePressed == null) {
                mMessagePressed = getResources().getDrawable(R.drawable.nav_message_pressed);
            }
            drawable = mMessagePressed;
        } else {
            mMessageTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (mMessageNormal == null) {
                mMessageNormal = getResources().getDrawable(R.drawable.nav_message_normal);
            }
            drawable = mMessageNormal;
        }
        if (null != drawable) {// 此处出现过NP问题，加保护
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mMessageTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void setMineText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMineTab.setTextColor(getResources().getColor(R.color.tab_pressed_color));
            if (mMinePressed == null) {
                mMinePressed = getResources().getDrawable(R.drawable.nav_mine_pressed);
            }
            drawable = mMinePressed;
        } else {
            mMineTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (mMineNormal == null) {
                mMineNormal = getResources().getDrawable(R.drawable.nav_mine_normal);
            }
            drawable = mMineNormal;
        }
        if (null != drawable) {// 此处出现过NP问题，加保护
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mMineTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        if (TAB_HOME.equals(tabId)) {
            setHomeText(true);
            setMessageText(false);
            setMineText(false);
        }else if (TAB_MESSAGE.equals(tabId)) {
            setHomeText(false);
            setMessageText(true);
            setMineText(false);
        }else if (TAB_MINE.equals(tabId)) {
            setHomeText(false);
            setMessageText(false);
            setMineText(true);
        }
    }
}
