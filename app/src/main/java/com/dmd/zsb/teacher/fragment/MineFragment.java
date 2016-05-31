package com.dmd.zsb.teacher.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmd.tutor.base.BaseWebActivity;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.mvp.presenter.impl.MinePresenterImpl;
import com.dmd.zsb.mvp.view.MineView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.DemandActivity;
import com.dmd.zsb.teacher.activity.EvaluationActivity;
import com.dmd.zsb.teacher.activity.OrderActivity;
import com.dmd.zsb.teacher.activity.SettingActivity;
import com.dmd.zsb.teacher.activity.SignInActivity;
import com.dmd.zsb.teacher.activity.VouchersActivity;
import com.dmd.zsb.teacher.activity.WalletActivity;
import com.dmd.zsb.teacher.activity.base.BaseFragment;
import com.dmd.zsb.protocol.response.mineResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;


public class MineFragment extends BaseFragment implements MineView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;

    @Bind(R.id.mine_wallet)
    LinearLayout mineWallet;
    @Bind(R.id.mine_order)
    LinearLayout mineOrder;
    @Bind(R.id.mine_evaluation)
    TextView mineEvaluation;
    @Bind(R.id.mine_demand)
    TextView mineDemand;
    @Bind(R.id.mine_vouchers)
    TextView mineVouchers;
    @Bind(R.id.mine_about_us)
    TextView mineAboutUs;
    @Bind(R.id.mine_setting)
    TextView mineSetting;

    @Bind(R.id.img_top_bg)
    ImageView imgTopBg;
    @Bind(R.id.tv_signin)
    TextView tvSignin;
    @Bind(R.id.user_avatar)
    RoundedImageView userAvatar;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.ll_signature)
    LinearLayout llSignature;


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
    public void onResume() {
        super.onResume();
        topBarBack.setVisibility(View.GONE);
        topBarTitle.setText("我的");
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {

            tvSignin.setVisibility(View.GONE);
            imgTopBg.setVisibility(View.VISIBLE);
            userAvatar.setVisibility(View.VISIBLE);
            llSignature.setVisibility(View.VISIBLE);
            if (XmlDB.getInstance(mContext).getKeyBooleanValue("ChangeAvatar", false)){
                minePresenter = new MinePresenterImpl(mContext, this);
                minePresenter.onMineInfo();
                XmlDB.getInstance(mContext).saveKey("ChangeAvatar",false);
            }
        } else {
            tvSignin.setVisibility(View.VISIBLE);
            imgTopBg.setVisibility(View.GONE);
            userAvatar.setVisibility(View.GONE);
            llSignature.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        minePresenter = new MinePresenterImpl(mContext, this);
        topBarBack.setVisibility(View.GONE);
        topBarTitle.setText("我的");
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
            tvSignin.setVisibility(View.GONE);
            imgTopBg.setVisibility(View.VISIBLE);
            userAvatar.setVisibility(View.VISIBLE);
            llSignature.setVisibility(View.VISIBLE);
            minePresenter.onMineInfo();
        } else {
            tvSignin.setVisibility(View.VISIBLE);
            imgTopBg.setVisibility(View.GONE);
            userAvatar.setVisibility(View.GONE);
            llSignature.setVisibility(View.GONE);
        }
    }

    @Override
    public void setView(mineResponse response) {
        //fragment频繁切换imgTopBg和userAvatar有丢失的可能
        //解决方法  保存fragment状态 保存view
        if (imgTopBg!=null)
        Picasso.with(mContext).load(response.user_large_img).into(imgTopBg);
        if (userAvatar!=null)
        Picasso.with(mContext).load(response.user_avatar).into(userAvatar);
        if (tvSignature!=null)
        tvSignature.setText(response.user_signature);
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @OnClick({R.id.tv_signin, R.id.mine_wallet, R.id.mine_order, R.id.mine_evaluation, R.id.mine_demand, R.id.mine_vouchers, R.id.mine_about_us, R.id.mine_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signin:
                readyGo(SignInActivity.class);
                ((Activity)mContext).finish();
                break;
            case R.id.mine_wallet:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
                    readyGo(WalletActivity.class);
                } else {
                    showToast("请先登录");
                }
                break;
            case R.id.mine_order:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
                    readyGo(OrderActivity.class);
                } else {
                    showToast("请先登录");
                }
                break;
            case R.id.mine_evaluation:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
                    readyGo(EvaluationActivity.class);
                } else {
                    showToast("请先登录");
                }
                break;
            case R.id.mine_demand:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
                    readyGo(DemandActivity.class);
                } else {
                    showToast("请先登录");
                }
                break;
            case R.id.mine_vouchers:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
                    readyGo(VouchersActivity.class);
                } else {
                    showToast("请先登录");
                }
                break;
            case R.id.mine_about_us:
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, "http://www.cqdmd.com/TutorClient/about/about.html");
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "关于我们");
                readyGo(BaseWebActivity.class, bundle);
                break;
            case R.id.mine_setting:
                readyGo(SettingActivity.class);
                break;
        }
    }

}
