package com.dmd.zsb.teacher.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.login.YWLoginCode;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.SignUpPresenterImpl;
import com.dmd.zsb.mvp.view.SignUpView;
import com.dmd.zsb.openim.LoginHelper;
import com.dmd.zsb.openim.Notification;
import com.dmd.zsb.openim.NotificationInitHelper;
import com.dmd.zsb.openim.UserProfileHelper;
import com.dmd.zsb.protocol.response.signupResponse;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.widgets.ToastView;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class SignUpActivity extends BaseActivity implements SignUpView, View.OnClickListener {

    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password_again)
    EditText etPasswordAgain;
    @Bind(R.id.btn_signup_complete)
    Button btnSignupComplete;

    private SignUpPresenterImpl signUpPresenter;
    private String mobile;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_sign_up;
    }
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        // 打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {

                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    mobile = (String) phoneMap.get("phone");

                    //初始化openIm
                    init(mobile, getString(R.string.app_key));
                }
            }
        });
        registerPage.show(this);
        etNickname.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        etPasswordAgain.setOnClickListener(this);
        btnSignupComplete.setOnClickListener(this);
        signUpPresenter = new SignUpPresenterImpl(mContext, this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    /**
     * 应用透明
     */
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
        String nickname = etNickname.getText().toString().trim();
        String password = etPassword.getText().toString();
        String password_again = etPasswordAgain.getText().toString();
        switch (v.getId()) {
            case R.id.btn_signup_complete:
                if ("".equals(nickname)) {
                    ToastView toast = new ToastView(mContext, getString(R.string.please_input_nickname));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etNickname.setText("");
                    etNickname.requestFocus();
                } else if (nickname.length() < 1 || nickname.length() > 16) {
                    ToastView toast = new ToastView(mContext, getString(R.string.nickname_wrong_format_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etNickname.requestFocus();
                } else if ("".equals(password)) {
                    ToastView toast = new ToastView(mContext, getString(R.string.please_input_password));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPassword.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    ToastView toast = new ToastView(mContext, getString(R.string.password_wrong_format_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPassword.requestFocus();
                } else if (!password.equals(password_again)) {
                    ToastView toast = new ToastView(mContext, getString(R.string.two_passwords_differ_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPasswordAgain.requestFocus();
                } else {
                    CloseKeyBoard();
                    btnSignupComplete.setClickable(false);
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("client_type", Constants.PLATFORM);
                        jsonObject.put("location", XmlDB.getInstance(mContext).getKeyString("addr","未取得定位地址"));
                        jsonObject.put("lat", XmlDB.getInstance(mContext).getKeyFloatValue("latitude", 0)+"");
                        jsonObject.put("lon", XmlDB.getInstance(mContext).getKeyFloatValue("longitude", 0)+"");
                        jsonObject.put("role", Constants.USER_ROLE);
                        jsonObject.put("nickname", nickname);
                        jsonObject.put("mobile", mobile);
                        jsonObject.put("password", password);
                    }catch (JSONException j){

                    }

                    signUpPresenter.signUp(jsonObject);
                }
                break;
        }
    }

    @Override
    public void navigateToHome(final signupResponse response) {
        LoginHelper.getInstance().login_Sample(mobile, etPassword.getText().toString(), getString(R.string.app_key), new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {

                YWLog.i(TAG_LOG, "login success!");
                hideLoading();
                Toast.makeText(mContext, "登录成功",Toast.LENGTH_SHORT).show();
                btnSignupComplete.setClickable(true);
                saveIdPasswordToLocal(mobile, etPassword.getText().toString());
                XmlDB.getInstance(mContext).saveKey("uid",response.uid);
                XmlDB.getInstance(mContext).saveKey("sid",response.sid);
                XmlDB.getInstance(mContext).saveKey("isLogin", true);
                Bundle bundle=new Bundle();
                bundle.putString(MainActivity.LOGIN_SUCCESS,"loginSuccess");
                readyGo(MainActivity.class,bundle);
                finish();
                
            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                hideLoading();
                if (errorCode == YWLoginCode.LOGON_FAIL_INVALIDUSER) { //若用户不存在，则提示使用游客方式登陆
                    showTip(errorMessage);
                } else {
                    btnSignupComplete.setClickable(true);
                    YWLog.w(TAG_LOG, "登录失败，错误码：" + errorCode + "  错误信息：" + errorMessage);
                    Notification.showToastMsg(mContext, errorMessage);
                }
            }
        });

    }

    @Override
    public void showTip(String msg) {
        hideLoading();
        btnSignupComplete.setClickable(true);
        ToastView toast = new ToastView(mContext, msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // 关闭键盘
    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etNickname.getWindowToken(), 0);
    }
    //=================================================open im========================================================
    private void init(String mobile, String appKey) {
        //初始化imkit
        LoginHelper.getInstance().initIMKit(mobile, appKey);
        //自定义头像和昵称回调初始化(如果不需要自定义头像和昵称，则可以省去)
        UserProfileHelper.initProfileCallback();
        //通知栏相关的初始化
        NotificationInitHelper.init();

    }

    /**
     * 保存登录的用户名密码到本地
     *
     * @param mobile
     * @param password
     */
    private void saveIdPasswordToLocal(String mobile, String password) {
        XmlDB.getInstance(mContext).saveKey("mobile", mobile);
        XmlDB.getInstance(mContext).saveKey("password", password);
    }
}
