package com.dmd.zsb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmd.pay.AliPayActivity;
import com.dmd.pay.entity.PayInfo;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.utils.CashierInputFilter;

import butterknife.Bind;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.et_recharge)
    EditText etRecharge;
    @Bind(R.id.btn_recharge)
    Button btnRecharge;

    private InputFilter[] inputFilters={new CashierInputFilter()};

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recharge;
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
        topBarTitle.setText("充值");
        etRecharge.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etRecharge.setFilters(inputFilters);
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

    @OnClick({R.id.top_bar_back, R.id.btn_recharge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.btn_recharge:
                PayInfo payInfo=new PayInfo();
                payInfo.setName("充值服务");
                payInfo.setDesc("充值金额可以用来购买课时，商品等");
                payInfo.setPrice(Double.parseDouble(etRecharge.getText().toString()));
                payInfo.setRate(1.0);
                Bundle bundle=new Bundle();
                bundle.putSerializable("payInfo",payInfo);
                readyGoForResult(AliPayActivity.class,110,bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
