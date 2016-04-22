package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.mvp.presenter.impl.ProfilePresenterImpl;
import com.dmd.zsb.mvp.view.ProfileView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.OnClick;

public class BriefActivity extends BaseActivity implements ProfileView {


    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.et_brief)
    EditText etBrief;
    @Bind(R.id.text_num)
    TextView textNum;
    @Bind(R.id.btn_save)
    Button btnSave;

    private ProfilePresenterImpl briefPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_brief;
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
        briefPresenter=new ProfilePresenterImpl(this,mContext);
        topBarTitle.setText(getResources().getText(R.string.personal_brief));
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


    @OnClick({R.id.top_bar_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.btn_save:
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                jsonObject.addProperty("version", Constants.ZSBVERSION);
                jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                jsonObject.addProperty("brief",etBrief.getText().toString());
                briefPresenter.onChangeProfile(jsonObject);
                break;
        }
    }

    @Override
    public void toSettingView() {
        finish();
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }
}
