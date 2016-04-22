package com.dmd.zsb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.SplashPresenterImpl;
import com.dmd.zsb.mvp.view.SplashView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;

import butterknife.Bind;
import cn.smssdk.SMSSDK;

public class SplashActivity extends BaseActivity implements SplashView {

    @Bind(R.id.splash_image)
    ImageView splashImage;
    @Bind(R.id.splash_version_name)
    TextView splashVersionName;
    @Bind(R.id.splash_copyright)
    TextView splashCopyright;
    private SplashPresenterImpl mSplashPresenter = null;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mSplashPresenter = new SplashPresenterImpl(mContext, this);
        mSplashPresenter.initialized();
        mSplashPresenter.loadingInitData(null);
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
    public void animateBackgroundImage(Animation animation) {
        if (XmlDB.getInstance(this).getKeyBooleanValue("isFirstRunLead", true)) {
            //进入引导页
            Intent intent = new Intent(this, LeadActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else {
            splashImage.startAnimation(animation);
        }
    }

    @Override
    public void initializeViews(String versionName, String copyright, int backgroundResId) {
        SMSSDK.initSDK(this, Constants.SMSAPPKEY, Constants.SMSAPPSECRET);
        splashCopyright.setText(copyright);
        splashVersionName.setText(versionName);
        splashImage.setImageResource(backgroundResId);
    }

    @Override
    public void initializeUmengConfig() {

    }

    @Override
    public void navigateToHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
