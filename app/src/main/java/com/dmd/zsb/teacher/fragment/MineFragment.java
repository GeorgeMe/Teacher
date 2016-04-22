package com.dmd.zsb.teacher.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.MinePresenterImpl;
import com.dmd.zsb.mvp.view.MineView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.AboutUsActivity;
import com.dmd.zsb.teacher.activity.EvaluationActivity;
import com.dmd.zsb.teacher.activity.OrderActivity;
import com.dmd.zsb.teacher.activity.SettingActivity;
import com.dmd.zsb.teacher.activity.SignInActivity;
import com.dmd.zsb.teacher.activity.TransactionActivity;
import com.dmd.zsb.teacher.activity.WalletActivity;
import com.dmd.zsb.teacher.activity.base.BaseFragment;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements MineView{

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.mine_sign_in)
    TextView mineSignIn;
    @Bind(R.id.mine_sign_out_header)
    LinearLayout mineSignOutHeader;
    @Bind(R.id.mine_header_img)
    ImageView mineHeaderImg;
    @Bind(R.id.mine_name)
    TextView mineName;
    @Bind(R.id.mine_type)
    TextView mineType;
    @Bind(R.id.mine_sex)
    TextView mineSex;
    @Bind(R.id.mine_address)
    TextView mineAddress;
    @Bind(R.id.mine_charging)
    TextView mineCharging;
    @Bind(R.id.mine_subjects)
    TextView mineSubjects;
    @Bind(R.id.mine_modify_data)
    TextView mineModifyData;
    @Bind(R.id.mine_logout_header)
    LinearLayout mineLogoutHeader;
    @Bind(R.id.mine_wallet)
    LinearLayout mineWallet;
    @Bind(R.id.mine_order)
    LinearLayout mineOrder;
    @Bind(R.id.mine_evaluation)
    TextView mineEvaluation;
    @Bind(R.id.mine_transaction)
    TextView mineTransaction;
    @Bind(R.id.mine_about_us)
    TextView mineAboutUs;
    @Bind(R.id.mine_switch_account)
    TextView mineSwitchAccount;
    @Bind(R.id.mine_sign_out)
    TextView mineSignOut;

    private MinePresenterImpl minePresenter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("我的");
        topBarBack.setVisibility(View.GONE);
        if(XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            mineSignOutHeader.setVisibility(View.GONE);
            mineLogoutHeader.setVisibility(View.VISIBLE);
        }else {
            mineSignOutHeader.setVisibility(View.VISIBLE);
            mineLogoutHeader.setVisibility(View.GONE);
        }
        minePresenter=new MinePresenterImpl(mContext,this);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
        jsonObject.addProperty("version", Constants.ZSBVERSION);
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
        minePresenter.onMine(jsonObject);

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @OnClick({R.id.mine_sign_in, R.id.mine_modify_data, R.id.mine_wallet, R.id.mine_order, R.id.mine_evaluation, R.id.mine_transaction, R.id.mine_about_us, R.id.mine_switch_account, R.id.mine_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_sign_in:
                readyGo(SignInActivity.class);
                break;
            case R.id.mine_modify_data:
                readyGo(SettingActivity.class);
                break;
            case R.id.mine_wallet:
                readyGo(WalletActivity.class);
                break;
            case R.id.mine_order:
                readyGo(OrderActivity.class);
                break;
            case R.id.mine_evaluation:
                readyGo(EvaluationActivity.class);
                break;
            case R.id.mine_transaction:
                readyGo(TransactionActivity.class);
                break;
            case R.id.mine_about_us:
                readyGo(AboutUsActivity.class);
                break;
            case R.id.mine_switch_account:
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.title)
                        .content("切换账户")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();
                break;
            case R.id.mine_sign_out:
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.title)
                        .content("退出登录")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();
                break;
        }
    }

    @Override
    public void setMineView(JsonObject jsonObject) {
        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS+jsonObject.get("mineHeaderImg").getAsString()).into(mineHeaderImg);
        mineName.setText(jsonObject.get("mineName").getAsString());
        mineType.setText(jsonObject.get("mineType").getAsString());
        mineSex.setText(jsonObject.get("mineSex").getAsString());
        mineAddress.setText(jsonObject.get("mineAddress").getAsString());
        mineCharging.setText(jsonObject.get("mineCharging").getAsString());
        mineSubjects.setText(jsonObject.get("mineSubjects").getAsString());
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }
}
