package com.dmd.zsb.teacher.activity;


import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.utils.CheckBankCard;

import butterknife.Bind;
import butterknife.OnClick;

public class BankCardActivity extends BaseActivity {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.add_bank_card)
    Button addBankCard;
    @Bind(R.id.rl_none_card)
    RelativeLayout rlNoneCard;
    @Bind(R.id.et_cardholder)
    EditText etCardholder;
    @Bind(R.id.input_bank_card)
    EditText inputBankCard;
    @Bind(R.id.save_bank_card)
    Button saveBankCard;
    @Bind(R.id.rl_add_card)
    RelativeLayout rlAddCard;
    @Bind(R.id.bound_card)
    TextView boundCard;
    @Bind(R.id.change_bank_card)
    Button changeBankCard;
    @Bind(R.id.rl_change_card)
    RelativeLayout rlChangeCard;

    private String bankCard="";
    @Override
    protected void getBundleExtras(Bundle extras) {
        bankCard=extras.getString("bank_card");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bank_card;
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
        topBarTitle.setText("银行卡详情");

        if (StringUtils.StringIsEmpty(bankCard)){
            rlNoneCard.setVisibility(View.VISIBLE);
            rlAddCard.setVisibility(View.GONE);
            rlChangeCard.setVisibility(View.GONE);
        }else {
            rlNoneCard.setVisibility(View.GONE);
            rlAddCard.setVisibility(View.GONE);
            rlChangeCard.setVisibility(View.VISIBLE);
        }

        inputBankCard.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        //校验银行卡号码
        if (CheckBankCard.checkBankCard("622848")) {

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

    @OnClick({R.id.top_bar_back, R.id.add_bank_card, R.id.save_bank_card, R.id.change_bank_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.add_bank_card:
                rlNoneCard.setVisibility(View.GONE);
                rlAddCard.setVisibility(View.VISIBLE);
                rlChangeCard.setVisibility(View.GONE);

                break;
            case R.id.save_bank_card:
                rlNoneCard.setVisibility(View.GONE);
                rlAddCard.setVisibility(View.GONE);
                rlChangeCard.setVisibility(View.VISIBLE);

                break;
            case R.id.change_bank_card:
                rlNoneCard.setVisibility(View.GONE);
                rlAddCard.setVisibility(View.VISIBLE);
                rlChangeCard.setVisibility(View.GONE);

                break;
        }
    }
}
