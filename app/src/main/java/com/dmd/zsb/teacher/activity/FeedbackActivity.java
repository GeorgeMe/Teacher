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
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.FeedbackPresenterImpl;
import com.dmd.zsb.mvp.view.FeedbackView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.feedbackResponse;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void navigateToSetting(feedbackResponse data) {
        if (data.errno==0){
            new AlertDialogWrapper.Builder(this)
                    .setTitle(R.string.title)
                    .setMessage(data.msg)
                    .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }else {
            showToast(data.msg);
        }
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
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("appkey", Constants.ZSBAPPKEY);
                    jsonObject.put("version", Constants.ZSBVERSION);
                    jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                    jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                    jsonObject.put("feedback",feedbackEdittext.getText().toString());
                }catch (JSONException j){

                }

                feedbackPresenter.seedFeedback(jsonObject);
                break;
        }
    }
}
