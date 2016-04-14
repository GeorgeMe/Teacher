package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.VouchersEntity;
import com.dmd.zsb.entity.response.VouchersResponse;
import com.dmd.zsb.mvp.presenter.impl.VouchersPresenterImpl;
import com.dmd.zsb.mvp.view.VouchersView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VouchersActivity extends BaseActivity implements VouchersView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    @Bind(R.id.vouchers_list_view)
    LoadMoreListView vouchersListView;
    @Bind(R.id.vouchers_list_swipe_layout)
    XSwipeRefreshLayout vouchersListSwipeLayout;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;


    private VouchersPresenterImpl vouchersPresenter;
    private ListViewDataAdapter<VouchersEntity> mListViewDataAdapter;
    private int page =1;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_vouchers;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return vouchersListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("代金券");
        vouchersPresenter = new VouchersPresenterImpl(mContext, this);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        vouchersPresenter.onVouchers(Constants.EVENT_REFRESH_DATA, jsonObject);
        mListViewDataAdapter = new ListViewDataAdapter<VouchersEntity>(new ViewHolderCreator<VouchersEntity>() {
            @Override
            public ViewHolderBase<VouchersEntity> createViewHolder(int position) {
                return new ViewHolderBase<VouchersEntity>() {
                    ImageView img_vouchers;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.vouchers_list_item, null);
                        img_vouchers = ButterKnife.findById(view, R.id.img_vouchers);
                        return view;
                    }

                    @Override
                    public void showData(int position, VouchersEntity itemData) {
                        Picasso.with(mContext).load(ApiConstants.Urls.API_BASE_URLS+itemData.getImg_path()).into(img_vouchers);
                    }
                };
            }
        });
        vouchersListView.setAdapter(mListViewDataAdapter);
        vouchersListView.setOnLoadMoreListener(this);
        vouchersListView.setOnItemClickListener(this);

        vouchersListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        vouchersListSwipeLayout.setOnRefreshListener(this);
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

    @Override
    public void navigateToVouchersDetail(int position, VouchersEntity itemData) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VouchersEntity vouchersEntity = (VouchersEntity) parent.getItemAtPosition(position);
        navigateToVouchersDetail(position, vouchersEntity);
    }

    @Override
    public void onLoadMore() {
        page=page+1;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        vouchersPresenter.onVouchers(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        vouchersPresenter.onVouchers(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @Override
    public void refreshListData(VouchersResponse data) {
        if (vouchersListSwipeLayout != null)
            vouchersListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getVouchersEntities().size() >= 2) {
                if (mListViewDataAdapter != null) {
                    mListViewDataAdapter.getDataList().clear();
                    mListViewDataAdapter.getDataList().addAll(data.getVouchersEntities());
                    mListViewDataAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                vouchersListView.setCanLoadMore(true);
            else
                vouchersListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(VouchersResponse data) {
        if (vouchersListView != null)
            vouchersListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewDataAdapter != null) {
                mListViewDataAdapter.getDataList().addAll(data.getVouchersEntities());
                mListViewDataAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
                vouchersListView.setCanLoadMore(true);
            else
                vouchersListView.setCanLoadMore(false);
        }
    }

    @OnClick(R.id.top_bar_back)
    public void onClick() {
        finish();
    }
}
