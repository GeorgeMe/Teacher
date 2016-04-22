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
import com.dmd.zsb.mvp.presenter.impl.FeedbackPresenterImpl;
import com.dmd.zsb.mvp.view.FeedbackView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements FeedbackView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.feedback_edittext)
    EditText feedbackEdittext;
    @Bind(R.id.feedback_button)
    Button feedbackButton;
    private FeedbackPresenterImpl feedbackPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_feedback;
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
        topBarTitle.setText(getResources().getText(R.string.feedback));
        feedbackPresenter=new FeedbackPresenterImpl(mContext,this);
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
    public void navigateToHome() {
        showToast("已收到反馈，谢谢");
        readyGoThenKill(MainActivity.class);
    }

    @Override
    public void showTip(String msg) {
        showTip(msg);
    }


    @OnClick({R.id.top_bar_back, R.id.feedback_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.feedback_button:
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                jsonObject.addProperty("version", Constants.ZSBVERSION);
                jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                jsonObject.addProperty("feedback",feedbackEdittext.getText().toString());
                feedbackPresenter.seedFeedback(jsonObject);
                break;
        }
    }
}
