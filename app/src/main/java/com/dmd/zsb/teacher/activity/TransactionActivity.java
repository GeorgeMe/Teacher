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
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.TransactionEntity;
import com.dmd.zsb.entity.response.TransactionResponse;
import com.dmd.zsb.mvp.presenter.impl.TransactionPresentterImpl;
import com.dmd.zsb.mvp.view.TransactionView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransactionActivity extends BaseActivity implements TransactionView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.fragment_transaction_list_view)
    LoadMoreListView fragmentTransactionListView;
    @Bind(R.id.fragment_transaction_list_swipe_layout)
    XSwipeRefreshLayout fragmentTransactionListSwipeLayout;

    private TransactionPresentterImpl transactionPresentter;

    private ListViewDataAdapter<TransactionEntity> mListViewAdapter;
    private int page = 1;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_transaction;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentTransactionListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("我的交易请求");
        transactionPresentter=new TransactionPresentterImpl(mContext,this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentTransactionListSwipeLayout) {
                fragmentTransactionListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                        jsonObject.addProperty("version", Constants.ZSBVERSION);
                        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.addProperty("page", page);
                        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                        transactionPresentter.onTransaction(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                    jsonObject.addProperty("version", Constants.ZSBVERSION);
                    jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.addProperty("page", page);
                    jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                    transactionPresentter.onTransaction(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }
        mListViewAdapter=new ListViewDataAdapter<TransactionEntity>(new ViewHolderCreator<TransactionEntity>() {
            @Override
            public ViewHolderBase<TransactionEntity> createViewHolder(int position) {
                return new ViewHolderBase<TransactionEntity>() {
                    ImageView img_header;
                    TextView tv_name,tv_appointed_time, tv_charging, tv_curriculum, tv_address, tv_note;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.transaction_list_item, null);
                        img_header = ButterKnife.findById(view, R.id.img_header);
                        tv_name = ButterKnife.findById(view, R.id.tv_name);
                        tv_appointed_time = ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging = ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum = ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_address = ButterKnife.findById(view, R.id.tv_address);
                        tv_note = ButterKnife.findById(view, R.id.tv_note);
                        return view;
                    }

                    @Override
                    public void showData(int position, TransactionEntity itemData) {
                        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.getImg_header()).into(img_header);
                        tv_name.setText(itemData.getName());
                        tv_appointed_time.setText(itemData.getAppointed_time());
                        tv_charging.setText(itemData.getCharging());
                        tv_curriculum.setText(itemData.getCurriculum());
                        tv_address.setText(itemData.getAddress());
                        tv_note.setText(itemData.getNote());
                    }
                };
            }
        });

        fragmentTransactionListView.setAdapter(mListViewAdapter);
        fragmentTransactionListView.setOnLoadMoreListener(this);
        fragmentTransactionListView.setOnItemClickListener(this);

        fragmentTransactionListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentTransactionListSwipeLayout.setOnRefreshListener(this);

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

    @OnClick(R.id.top_bar_back)
    public void onClick() {
        finish();
    }

    @Override
    public void navigateToTransactionDetail(TransactionEntity data) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",data);
        readyGo(TransactionDetailActivity.class,bundle);
    }

    @Override
    public void refreshListData(TransactionResponse data) {
        if (fragmentTransactionListSwipeLayout != null)
            fragmentTransactionListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getTransactionEntities().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getTransactionEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                fragmentTransactionListView.setCanLoadMore(true);
            else
                fragmentTransactionListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(TransactionResponse data) {
        if (fragmentTransactionListView != null)
            fragmentTransactionListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getTransactionEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
                fragmentTransactionListView.setCanLoadMore(true);
            else
                fragmentTransactionListView.setCanLoadMore(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TransactionEntity data=(TransactionEntity)parent.getItemAtPosition(position);
        navigateToTransactionDetail(data);
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
        jsonObject.addProperty("version", Constants.ZSBVERSION);
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        transactionPresentter.onTransaction(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
        jsonObject.addProperty("version", Constants.ZSBVERSION);
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", 1);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        transactionPresentter.onTransaction(Constants.EVENT_REFRESH_DATA, jsonObject);
    }
}
