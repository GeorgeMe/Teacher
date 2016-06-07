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
import com.dmd.tutor.utils.CommonUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.RechargePresenterImpl;
import com.dmd.zsb.mvp.view.RechargeView;
import com.dmd.zsb.protocol.response.rechargeResponse;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.utils.CashierInputFilter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity implements RechargeView {
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.et_recharge)
    EditText etRecharge;
    @Bind(R.id.btn_recharge)
    Button btnRecharge;

    private PayInfo payInfo=null;
    private InputFilter[] inputFilters={new CashierInputFilter()};

    private RechargePresenterImpl rechargeInteractor;
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
        rechargeInteractor=new RechargePresenterImpl(mContext,this);
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
                if (!CommonUtils.isEmpty(etRecharge.getText().toString())){
                    if (Double.parseDouble(etRecharge.getText().toString())<0.01){
                        showToast("请输入大于0.01的金额");
                    }else {
                        JSONObject jsonObject =new JSONObject();
                        try {
                            jsonObject.put("appkey", Constants.ZSBAPPKEY);
                            jsonObject.put("version", Constants.ZSBVERSION);
                            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));

                            jsonObject.put("body", "充值服务");
                            jsonObject.put("discount", "1.0");
                            jsonObject.put("voucher", "0");
                            jsonObject.put("price", etRecharge.getText().toString());
                            jsonObject.put("subject", "充值金额可以用来购买课时，商品等");

                            rechargeInteractor.onRecharge(jsonObject);
                        }catch (JSONException j){

                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==110){
            if (resultCode==111) {
                setResult(10001);
                if (data.getIntExtra("resultStatus",1000)==9000){
                    showToast("充值成功");
                    finish();
                }else if (data.getIntExtra("resultStatus",1000)==8000){
                    showToast("支付结果确认中");
                    finish();
                }else {
                    showToast("充值失败");
                    finish();
                }
            }
        }

    }

    @Override
    public void onRechargeView(rechargeResponse response) {
        payInfo=new PayInfo();
        payInfo.setName(response.recharge.body);
        payInfo.setDesc(response.recharge.subject);
        payInfo.setPrice(Double.parseDouble(response.recharge.price));
        payInfo.setRate(1.0);
        payInfo.setOrder_sn(response.recharge.out_trade_no);
        if (payInfo!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("payInfo",payInfo);
            readyGoForResult(AliPayActivity.class,110,bundle);
        }
    }
}
