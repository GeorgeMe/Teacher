package com.dmd.zsb.teacher.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.SignInActivity;
import com.dmd.zsb.teacher.activity.base.BaseFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_signin)
    TextView tvSignin;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_message;
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
        //return fragmentMessageListSwipeLayout;
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarBack.setVisibility(View.GONE);
        topBarTitle.setText("消息");

        SpannableString sp = new SpannableString("您尚未登录，点击登录");
        sp.setSpan(new ForegroundColorSpan(Color.BLUE), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan(1.4f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //2.0f表示默认字体大小的两倍
        tvSignin.setText(sp);

        tvSignin.setOnClickListener(this);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public void onClick(View v) {
        if (v == tvSignin) {
            readyGo(SignInActivity.class);
            ((Activity)mContext).finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
