package com.dmd.zsb.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.WalletPresenterImpl;
import com.dmd.zsb.mvp.view.WalletView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.walletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity implements WalletView{

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.wallet_balance)
    TextView walletBalance;
    @Bind(R.id.wallet_transaction_detail)
    TextView walletTransactionDetail;
    @Bind(R.id.wallet_cumulative_class)
    TextView walletCumulativeClass;
    @Bind(R.id.wallet_earn_money)
    TextView walletEarnMoney;
    @Bind(R.id.wallet_recharge)
    TextView walletRecharge;
    @Bind(R.id.wallet_withdrawals)
    TextView walletWithdrawals;
    @Bind(R.id.wallet_bank_card)
    TextView walletBankCard;

    private WalletPresenterImpl walletPresenter;

    public String   buyer_id;
    public String   balance;
    public String   bank_card;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wallet;
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
        topBarTitle.setText("我的钱包");
        walletPresenter=new WalletPresenterImpl(this,mContext);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        }catch (JSONException j){

        }
        walletPresenter.onWalletInfo(jsonObject);
    }

    @Override
    public void setView(walletResponse response) {
        balance=response.balance;
        bank_card=response.bank_card;
        buyer_id=response.buyer_id;
        walletBalance.setText(response.balance+" ￥");

        walletCumulativeClass.setText(response.total_hours+" 小时");
        walletEarnMoney.setText(response.total_amount+" ￥");

    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
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

    @OnClick({R.id.top_bar_back, R.id.wallet_transaction_detail, R.id.wallet_cumulative_class, R.id.wallet_earn_money, R.id.wallet_recharge, R.id.wallet_withdrawals, R.id.wallet_bank_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.wallet_transaction_detail:
                Bundle transaction=new Bundle();
                transaction.putString("buyer_id",buyer_id);
                readyGo(TransactionDetailActivity.class,transaction);
                break;
            case R.id.wallet_recharge:
                readyGoForResult(RechargeActivity.class,10001);
                break;
            case R.id.wallet_withdrawals:
                Bundle withdrawals=new Bundle();
                withdrawals.putString("bank_card",bank_card);
                withdrawals.putString("transfer_amount",balance);
                readyGo(WithDrawalsActivity.class,withdrawals);
                break;
            case R.id.wallet_bank_card:
                Bundle bankCard=new Bundle();
                bankCard.putString("bank_card",bank_card);
                readyGo(BankCardActivity.class,bankCard);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10001){
            super.onResume();
        }
    }
}
