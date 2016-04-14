package com.dmd.zsb.teacher.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.dialog.AlertDialogWrapper;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.entity.TransactionEntity;
import com.dmd.zsb.mvp.presenter.impl.TransactionDetailPresenterImpl;
import com.dmd.zsb.mvp.view.TransactionDetailView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

public class TransactionDetailActivity extends BaseActivity implements TransactionDetailView{

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.img_header)
    ImageView imgHeader;
    @Bind(R.id.tv_appointed_time)
    TextView tvAppointedTime;
    @Bind(R.id.tv_charging)
    TextView tvCharging;
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_note)
    TextView tvNote;
    @Bind(R.id.btn_refuse)
    Button btnRefuse;
    @Bind(R.id.btn_accept)
    Button btnAccept;
    private TransactionEntity data;
    private TransactionDetailPresenterImpl transactionDetailPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        data = (TransactionEntity) extras.getSerializable("data");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_transaction_detail;
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
        topBarTitle.setText("需求详情");
        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS+data.getImg_header()).into(imgHeader);
        tvAppointedTime.setText(data.getAppointed_time());
        tvCharging.setText(data.getCharging());
        tvSubject.setText(data.getCurriculum());
        tvAddress.setText(data.getAddress());
        tvType.setText(data.getName());
        tvNote.setText(data.getNote());
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

    @OnClick({R.id.top_bar_back, R.id.btn_refuse, R.id.btn_accept})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.btn_refuse:

                break;
            case R.id.btn_accept:
                transactionDetailPresenter=new TransactionDetailPresenterImpl(mContext,this);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                jsonObject.addProperty("oid",data.getOid());
                transactionDetailPresenter.onAcceptOrder(jsonObject);
                break;
        }
    }

    @Override
    public void navigateToTransaction() {
        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.title)
                .setMessage("您已接单，请做好备课准备，完成授课")
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
