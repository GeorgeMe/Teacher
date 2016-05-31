package com.dmd.zsb.teacher.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmd.dialog.AlertDialogWrapper;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.ChangePasswordPresenterImpl;
import com.dmd.zsb.mvp.view.ChangePasswordView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.now_password)
    EditText nowPassword;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.re_new_password)
    EditText reNewPassword;
    @Bind(R.id.save)
    Button save;
    private ChangePasswordPresenterImpl changePasswordPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_change_password;
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
        topBarTitle.setText(getResources().getText(R.string.new_password));
        changePasswordPresenter=new ChangePasswordPresenterImpl(this,mContext);
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

    @OnClick({R.id.top_bar_back, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.save:
                if (StringUtils.StringIsEmpty(nowPassword.getText().toString())){
                    showToast("请输入原密码");
                }else if (StringUtils.StringIsEmpty(newPassword.getText().toString())){
                    showToast("密码不能为空");
                }else if (reNewPassword.getText().toString().length()<6){
                    showToast("密码不能少于6位");
                }else if (!newPassword.getText().toString().equals(reNewPassword.getText().toString())){
                    showToast("两次密码不一致");
                }else if (newPassword.getText().toString().equals(reNewPassword.getText().toString())){
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));

                        jsonObject.put("nowPassword",nowPassword.getText().toString());
                        jsonObject.put("newPassword",newPassword.getText().toString());
                    }catch (JSONException j){

                    }

                    changePasswordPresenter.onChangePassword(jsonObject);
                }

                break;
        }
    }

    @Override
    public void toSettingView() {
        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.title)
                .setMessage("修改成功")
                .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }
}
