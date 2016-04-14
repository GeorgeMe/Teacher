package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.mvp.presenter.impl.WalletPresenterImpl;
import com.dmd.zsb.mvp.view.WalletView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;

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
    @Bind(R.id.wallet_vouchers)
    TextView walletVouchers;

    private WalletPresenterImpl walletPresenter;
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        walletPresenter.onWalletInfo(jsonObject);
    }

    @Override
    public void setView(JsonObject jsonObject) {
        walletBalance.setText(jsonObject.get("balance").getAsString());
        walletCumulativeClass.setText(jsonObject.get("total_hours").getAsString());
        walletEarnMoney.setText(jsonObject.get("total_amount").getAsString());
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

    @OnClick({R.id.top_bar_back, R.id.wallet_transaction_detail, R.id.wallet_cumulative_class, R.id.wallet_earn_money, R.id.wallet_recharge, R.id.wallet_withdrawals, R.id.wallet_bank_card, R.id.wallet_vouchers})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.wallet_transaction_detail:
                readyGo(WalletTransationActivity.class);
                break;
            case R.id.wallet_recharge:
                readyGo(RechargeActivity.class);
                break;
            case R.id.wallet_withdrawals:
                readyGo(WithDrawalsActivity.class);
                break;
            case R.id.wallet_bank_card:
                readyGo(BankCardActivity.class);
                break;
            case R.id.wallet_vouchers:
                readyGo(VouchersActivity.class);
                break;
        }
    }
}
