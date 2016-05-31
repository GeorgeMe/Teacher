package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmd.tutor.base.BaseWebActivity;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.WithdrawalPresenterImpl;
import com.dmd.zsb.mvp.view.WithdrawalView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.withdrawalResponse;
import com.dmd.zsb.utils.CheckBankCard;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class WithDrawalsActivity extends BaseActivity implements WithdrawalView {


    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.bank_card)
    TextView bankCard;
    @Bind(R.id.payment_date)
    TextView paymentDate;
    @Bind(R.id.transfer_amount)
    EditText transferAmount;
    @Bind(R.id.withdrawals)
    Button withdrawals;
    @Bind(R.id.tv_note)
    TextView tvNote;

    public String   balance;
    public String   bank_card;


    private WithdrawalPresenterImpl withdrawalPresenter;


    @Override
    protected void getBundleExtras(Bundle extras) {
        balance=extras.getString("transfer_amount");
        bank_card=extras.getString("bank_card");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_with_drawals;
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
        topBarTitle.setText("申请提现");
        if (StringUtils.StringIsEmpty(bank_card)){
            bankCard.setText("没有找到银行卡");
        }else {
            if (CheckBankCard.checkBankCard(bank_card)){
                bankCard.setText(bank_card);
            }
        }
        if (StringUtils.StringIsEmpty(balance)){
            transferAmount.setHint("可提现金额 "+0.00+" ￥");
        }else {
            transferAmount.setHint("可提现金额 "+balance+" ￥");
        }

        withdrawalPresenter=new WithdrawalPresenterImpl(mContext,this);
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

    @OnClick({R.id.top_bar_back, R.id.withdrawals, R.id.tv_note})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.withdrawals:
                JSONObject jsonObject=new JSONObject();

                try {
                    jsonObject.put("appkey", Constants.ZSBAPPKEY);
                    jsonObject.put("version", Constants.ZSBVERSION);
                    jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.put("category", "余额转出");
                    jsonObject.put("transfer_amount", balance);
                }catch (JSONException j){

                }
                withdrawalPresenter.onWithdrawal(jsonObject);
                break;
            case R.id.tv_note:
                //给个网页说明
                Bundle bundle=new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL,"http://www.cqdmd.com/TutorClient/about/question.html");
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE,"常见问题");
                readyGo(BaseWebActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onWithdrawal(withdrawalResponse response) {
        if(response.errno==0){
            showToast(response.msg);
        }else {
            showToast(response.msg);
        }
    }
}
