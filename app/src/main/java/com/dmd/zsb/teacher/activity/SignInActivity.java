package com.dmd.zsb.teacher.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.CommonUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.SignInPresenterImpl;
import com.dmd.zsb.mvp.view.SignInView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.widgets.ToastView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Random;

import butterknife.Bind;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class SignInActivity extends BaseActivity implements SignInView, View.OnClickListener {

    @Bind(R.id.et_mobile)
    EditText etMobile;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_signup)
    TextView tvSignup;
    @Bind(R.id.signin_frame)
    FrameLayout signinFrame;
    private SignInPresenterImpl signInPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_sign_in;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return signinFrame;
    }

    @Override
    protected void initViewsAndEvents() {
        signInPresenter = new SignInPresenterImpl(mContext, this);
        etMobile.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        if (!CommonUtils.isEmpty(XmlDB.getInstance(mContext).getKeyString("mobile", "")))
            etMobile.setText(XmlDB.getInstance(mContext).getKeyString("mobile", ""));
        if (!CommonUtils.isEmpty(XmlDB.getInstance(mContext).getKeyString("password", "")))
            etPassword.setText(XmlDB.getInstance(mContext).getKeyString("password", ""));
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
    public void onClick(View v) {

        String mobile = etMobile.getText().toString();
        String password = etPassword.getText().toString();

        switch (v.getId()) {
            case R.id.tv_signup:
                // 打开注册页面
                CloseKeyBoard();
                //readyGoThenKill(SignUpVerifyActivity.class);
                break;
            case R.id.btn_login:
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else if ("".equals(password)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_password));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    ToastView toast = new ToastView(this, getString(R.string.please_enter_correct_password_format));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPassword.requestFocus();
                } else {
                    CloseKeyBoard();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("mobile", mobile);
                    jsonObject.addProperty("password", password);
                    signInPresenter.signIn(jsonObject);
                }
                break;
        }
    }
    // 提交用户信息
    private void registerUser(String country, String phone) {
        Log.e(TAG_LOG,country+"----"+phone);
    }
    // 关闭键盘
    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etMobile.getWindowToken(), 0);
    }

    @Override
    public void navigateToHome() {

        String mobile = etMobile.getText().toString();
        String password = etPassword.getText().toString();
        //加密再存本地
        XmlDB.getInstance(mContext).saveKey("mobile", mobile);
        XmlDB.getInstance(mContext).saveKey("password", password);
        readyGoThenKill(MainActivity.class);
    }
}
