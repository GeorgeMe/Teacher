package com.dmd.zsb.teacher.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dmd.dialog.AlertDialogWrapper;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.mvp.presenter.impl.OrderDetailPresenterImpl;
import com.dmd.zsb.mvp.view.OrderDetailView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements OrderDetailView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_appointed_time)
    TextView tvAppointedTime;
    @Bind(R.id.tv_charging)
    TextView tvCharging;
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_place)
    TextView tvPlace;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_note)
    TextView tvNote;
    @Bind(R.id.btn_work_done)
    Button btnWorkDone;

    private OrderEntity data;
    private OrderDetailPresenterImpl orderDetailPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        data=(OrderEntity)extras.getSerializable("data");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
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
        topBarTitle.setText("订单详情");

        orderDetailPresenter=new OrderDetailPresenterImpl(mContext,this);
        tvName.setText(data.getName());
        tvAppointedTime.setText(data.getAppointed_time());
        tvCharging.setText(data.getCharging());
        tvSubject.setText(data.getCurriculum());
        tvAddress.setText(data.getAddress());
        tvNote.setText(data.getName());
        tvType.setText(data.getType());
        tvPlace.setText(data.getPlace());

        if (!data.getState().equals("1")){
            btnWorkDone.setVisibility(View.GONE);
        }
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

    @OnClick({R.id.top_bar_back, R.id.btn_work_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.btn_work_done:
                new AlertDialogWrapper.Builder(this)
                        .setTitle(R.string.title)
                        .setMessage("请确认按约定完成授课")
                        .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                                jsonObject.addProperty("version", Constants.ZSBVERSION);
                                jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                                jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                                jsonObject.addProperty("order_status",2);
                                jsonObject.addProperty("oid",data.getOid());
                                orderDetailPresenter.onOrderDone(jsonObject);
                            }
                        }).setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void navigateToOrder() {

        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.title)
                .setMessage("订单完成，等待付款")
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
