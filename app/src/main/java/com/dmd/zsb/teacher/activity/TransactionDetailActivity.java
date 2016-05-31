package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.TransactionPresenterImpl;
import com.dmd.zsb.mvp.view.TransactionView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.transactionsResponse;
import com.dmd.zsb.protocol.table.TransactionsBean;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransactionDetailActivity extends BaseActivity implements TransactionView , LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.transaction_detail_list_list_view)
    LoadMoreListView transactionDetailListListView;
    @Bind(R.id.transaction_detail_list_swipe_layout)
    XSwipeRefreshLayout transactionDetailListSwipeLayout;

    private TransactionPresenterImpl transactionPresenter;
    private ListViewDataAdapter<TransactionsBean> mListViewDataAdapter;

    private int page =1;
    public String   buyer_id;
    @Override
    protected void getBundleExtras(Bundle extras) {
        buyer_id=extras.getString("buyer_id");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_transaction_detail;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return transactionDetailListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("交易明细");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("buyer_id", buyer_id);
            jsonObject.put("page",1);
            jsonObject.put("rows", UriHelper.PAGE_LIMIT);
        }catch (JSONException j){

        }
        transactionPresenter=new TransactionPresenterImpl(mContext,this);
        transactionPresenter.onTransaction(Constants.EVENT_REFRESH_DATA, jsonObject);
        mListViewDataAdapter=new ListViewDataAdapter<TransactionsBean>(new ViewHolderCreator<TransactionsBean>() {

            @Override
            public ViewHolderBase<TransactionsBean> createViewHolder(int position) {
                return new ViewHolderBase<TransactionsBean>() {
                    TextView out_trade_no,subject,body,gmt_payment,total_fee,trade_status;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view=layoutInflater.inflate(R.layout.transaction_detail_item,null);
                        out_trade_no=ButterKnife.findById(view, R.id.out_trade_no);
                        subject=ButterKnife.findById(view, R.id.subject);
                        gmt_payment=ButterKnife.findById(view, R.id.gmt_payment);
                        total_fee=ButterKnife.findById(view, R.id.total_fee);
                        trade_status=ButterKnife.findById(view, R.id.trade_status);
                        body=ButterKnife.findById(view, R.id.body);
                        return view;
                    }

                    @Override
                    public void showData(int position, TransactionsBean itemData) {
                        out_trade_no.setText(itemData.out_trade_no);
                        subject.setText(itemData.subject);
                        gmt_payment.setText(itemData.gmt_payment);
                        total_fee.setText(itemData.total_fee);
                        if ("TRADE_SUCCESS".equals(itemData.trade_status)){
                            trade_status.setText("交易成功");
                        }else if ("WAIT_BUYER_PAY".equals(itemData.trade_status)){
                            trade_status.setText("交易成功");
                        }
                        body.setText(itemData.body);
                    }
                };
            }
        });

        transactionDetailListListView.setAdapter(mListViewDataAdapter);
        transactionDetailListListView.setOnLoadMoreListener(this);

        transactionDetailListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        transactionDetailListSwipeLayout.setOnRefreshListener(this);
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
    public void onLoadMore() {
        page=page+1;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("buyer_id", buyer_id);
            jsonObject.put("page",page);
            jsonObject.put("rows", UriHelper.PAGE_LIMIT);
        }catch (JSONException j){

        }
        transactionPresenter.onTransaction(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("buyer_id", buyer_id);
            jsonObject.put("page",1);
            jsonObject.put("rows", UriHelper.PAGE_LIMIT);
        }catch (JSONException j){

        }
        transactionPresenter.onTransaction(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @Override
    public void refreshListData(transactionsResponse response) {
        if (transactionDetailListSwipeLayout != null)
            transactionDetailListSwipeLayout.setRefreshing(false);
        if (response.transactions != null) {
            if (response.transactions.size() >= 1) {
                if (mListViewDataAdapter != null) {
                    mListViewDataAdapter.getDataList().clear();
                    mListViewDataAdapter.getDataList().addAll(response.transactions);
                    mListViewDataAdapter.notifyDataSetChanged();
                }
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                transactionDetailListListView.setCanLoadMore(true);
            else
                transactionDetailListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(transactionsResponse response) {
        if (transactionDetailListListView != null)
            transactionDetailListListView.onLoadMoreComplete();
        if (response.transactions != null) {
            if (mListViewDataAdapter != null) {
                mListViewDataAdapter.getDataList().addAll(response.transactions);
                mListViewDataAdapter.notifyDataSetChanged();
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                transactionDetailListListView.setCanLoadMore(true);
            else
                transactionDetailListListView.setCanLoadMore(false);
        }
    }
}
