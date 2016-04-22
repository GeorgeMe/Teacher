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
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.mvp.presenter.impl.NickNamePresenterImpl;
import com.dmd.zsb.mvp.view.NickNameView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NickNameActivity extends BaseActivity implements NickNameView{
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.btn_save)
    Button btnSave;

    private NickNamePresenterImpl nickNamePresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nick_name;
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
        nickNamePresenter=new NickNamePresenterImpl(this,mContext);
        topBarTitle.setText(getResources().getText(R.string.nickname));
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
                jsonObject.addProperty("nickname",etNickname.getText().toString());
                nickNamePresenter.updateNickName(jsonObject);
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
