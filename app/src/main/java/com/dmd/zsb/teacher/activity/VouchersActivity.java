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
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.VouchersPresenterImpl;
import com.dmd.zsb.mvp.view.VouchersView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.vouchersResponse;
import com.dmd.zsb.protocol.table.VouchersBean;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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
    private ListViewDataAdapter<VouchersBean> mListViewDataAdapter;
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
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        }catch (JSONException j){

        }

        vouchersPresenter.onVouchers(Constants.EVENT_REFRESH_DATA, jsonObject);
        mListViewDataAdapter = new ListViewDataAdapter<VouchersBean>(new ViewHolderCreator<VouchersBean>() {
            @Override
            public ViewHolderBase<VouchersBean> createViewHolder(int position) {
                return new ViewHolderBase<VouchersBean>() {
                    ImageView img_vouchers;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.vouchers_list_item, null);
                        img_vouchers = ButterKnife.findById(view, R.id.img_vouchers);
                        return view;
                    }

                    @Override
                    public void showData(int position, VouchersBean itemData) {
                        Picasso.with(mContext).load(itemData.img_path).into(img_vouchers);
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
    public void navigateToVouchersDetail(int position, VouchersBean itemData) {
        if (itemData!=null)
            showToast(""+itemData.note);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VouchersBean vouchersBean = (VouchersBean) parent.getAdapter().getItem(position);
        navigateToVouchersDetail(position, vouchersBean);
    }

    @Override
    public void onLoadMore() {
        page=page+1;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        }catch (JSONException j){

        }
        vouchersPresenter.onVouchers(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        }catch (JSONException j){

        }

        vouchersPresenter.onVouchers(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @Override
    public void refreshListData(vouchersResponse response) {
        if (vouchersListSwipeLayout != null)
            vouchersListSwipeLayout.setRefreshing(false);
        if (response.vouchers != null) {
            if (response.vouchers.size() >= 1) {
                if (mListViewDataAdapter != null) {
                    mListViewDataAdapter.getDataList().clear();
                    mListViewDataAdapter.getDataList().addAll(response.vouchers);
                    mListViewDataAdapter.notifyDataSetChanged();
                }
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                vouchersListView.setCanLoadMore(true);
            else
                vouchersListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(vouchersResponse response) {
        if (vouchersListView != null)
            vouchersListView.onLoadMoreComplete();
        if (response.vouchers != null) {
            if (mListViewDataAdapter != null) {
                mListViewDataAdapter.getDataList().addAll(response.vouchers);
                mListViewDataAdapter.notifyDataSetChanged();
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
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
