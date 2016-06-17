package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.dialog.DialogAction;
import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.ConfirmPayPresenterImpl;
import com.dmd.zsb.mvp.presenter.impl.OrderPresenterImpl;
import com.dmd.zsb.mvp.view.ConfirmPayView;
import com.dmd.zsb.mvp.view.OrderView;
import com.dmd.zsb.protocol.response.confirmpayResponse;
import com.dmd.zsb.protocol.response.orderResponse;
import com.dmd.zsb.protocol.table.OrdersBean;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity implements OrderView,ConfirmPayView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    @Bind(R.id.my_order_menu_group)
    RadioGroup myOrderMenuGroup;
    @Bind(R.id.fragment_my_order_list_list_view)
    LoadMoreListView fragmentMyOrderListListView;
    @Bind(R.id.fragment_my_order_list_swipe_layout)
    XSwipeRefreshLayout fragmentMyOrderListSwipeLayout;
    @Bind(R.id.my_order_group_menu_incomplete)
    RadioButton myOrderGroupMenuIncomplete;
    @Bind(R.id.my_order_group_menu_recent_completed)
    RadioButton myOrderGroupMenuRecentCompleted;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;

    private OrderPresenterImpl orderPresenter;
    private ConfirmPayPresenterImpl confirmPayPresenter;
    private ListViewDataAdapter<OrdersBean> mListViewAdapter;
    private int page = 1;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentMyOrderListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("我的订单");
        myOrderGroupMenuIncomplete.setChecked(true);
        orderPresenter = new OrderPresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentMyOrderListSwipeLayout) {
                fragmentMyOrderListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("appkey", Constants.ZSBAPPKEY);
                            jsonObject.put("version", Constants.ZSBVERSION);
                            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                            jsonObject.put("page", page);
                            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                            if (myOrderGroupMenuIncomplete.isChecked()) {
                                jsonObject.put("order_status", 3);
                            } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
                                jsonObject.put("order_status", 4);
                            }
                        }catch (JSONException j){

                        }

                        orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.put("page", page);
                        jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                        if (myOrderGroupMenuIncomplete.isChecked()) {
                            jsonObject.put("order_status", 3);
                        } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
                            jsonObject.put("order_status", 4);
                        }
                    }catch (JSONException j){

                    }

                    orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }
        mListViewAdapter = new ListViewDataAdapter<OrdersBean>(new ViewHolderCreator<OrdersBean>() {
            @Override
            public ViewHolderBase<OrdersBean> createViewHolder(int position) {
                return new ViewHolderBase<OrdersBean>() {
                    TextView tv_oid, tv_created_at, tv_appointment_time, tv_subject, tv_text, tv_receiver_id, tv_distance, tv_location,tv_order_status,tv_offer_price;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.order_list_item, null);
                        tv_oid = ButterKnife.findById(view, R.id.tv_oid);
                        tv_created_at = ButterKnife.findById(view, R.id.tv_created_at);
                        tv_appointment_time = ButterKnife.findById(view, R.id.tv_appointment_time);
                        tv_subject = ButterKnife.findById(view, R.id.tv_subject);
                        tv_text = ButterKnife.findById(view, R.id.tv_text);
                        tv_receiver_id = ButterKnife.findById(view, R.id.tv_receiver_id);
                        tv_distance = ButterKnife.findById(view, R.id.tv_distance);
                        tv_location = ButterKnife.findById(view, R.id.tv_location);
                        tv_offer_price = ButterKnife.findById(view, R.id.tv_offer_price);
                        tv_order_status = ButterKnife.findById(view, R.id.tv_order_status);
                        return view;
                    }

                    @Override
                    public void showData(int position, OrdersBean itemData) {
                        tv_oid.setText(itemData.oid);
                        tv_created_at.setText(itemData.created_at);
                        tv_appointment_time.setText(itemData.appointment_time);
                        tv_subject.setText(itemData.subject);
                        tv_text.setText(itemData.text);
                        tv_receiver_id.setText(itemData.receiver_id);
                        tv_distance.setText(LocationManager.getDistance(Double.parseDouble(itemData.lat), Double.parseDouble(itemData.lon)));
                        tv_location.setText(itemData.location);
                        tv_offer_price.setText(itemData.offer_price);
                        if (itemData.order_status==3) {
                            tv_order_status.setText("待确认");
                        } else if (itemData.order_status==4) {
                            tv_order_status.setText("已确认");
                        }
                    }
                };
            }
        });

        fragmentMyOrderListListView.setAdapter(mListViewAdapter);
        fragmentMyOrderListListView.setOnLoadMoreListener(this);
        fragmentMyOrderListListView.setOnItemClickListener(this);

        fragmentMyOrderListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentMyOrderListSwipeLayout.setOnRefreshListener(this);

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
    public void navigateToOrderDetail(final OrdersBean itemData) {
        if (itemData!=null){
/*            Bundle bundle=new Bundle();
            bundle.putSerializable("data",itemData);
            readyGo(OrderDetailActivity.class,bundle);*/
            if(itemData.order_status==3){
                new MaterialDialog.Builder(mContext).content("确定对方已付款了么吗？").positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //提交的参数封装
                        confirmPay(itemData);
                        dialog.dismiss();
                    }
                }).show();
            }else if (itemData.order_status==4){
                showToast("该订单已确认付款");
            }
        }
    }
    private void confirmPay(OrdersBean itemData){
        try{
            confirmPayPresenter=new ConfirmPayPresenterImpl(mContext,this);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("oid",itemData.oid);
            confirmPayPresenter.onConfirmPay(jsonObject);
        }catch (JSONException j){}

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrdersBean ordersBean = (OrdersBean) parent.getAdapter().getItem(position);
        navigateToOrderDetail(ordersBean);
    }

    @Override
    public void refreshListData(orderResponse response) {
        if (fragmentMyOrderListSwipeLayout != null)
            fragmentMyOrderListSwipeLayout.setRefreshing(false);
        if (response.orders != null) {
            if (response.orders.size() >= 1) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.orders);
                    mListViewAdapter.notifyDataSetChanged();
                }
            }else {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentMyOrderListListView!=null){
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                    fragmentMyOrderListListView.setCanLoadMore(true);
                else
                    fragmentMyOrderListListView.setCanLoadMore(false);
            }
        }else {
            mListViewAdapter.getDataList().clear();
            mListViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addMoreListData(orderResponse response) {
        if (fragmentMyOrderListListView != null)
            fragmentMyOrderListListView.onLoadMoreComplete();
        if (response.orders != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(response.orders);
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentMyOrderListListView!=null){
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                    fragmentMyOrderListListView.setCanLoadMore(true);
                else
                    fragmentMyOrderListListView.setCanLoadMore(false);
            }
        }
    }


    @Override
    public void onLoadMore() {
        page = page + 1;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("page", page);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
            if (myOrderGroupMenuIncomplete.isChecked()) {
                jsonObject.put("order_status", 3);
            } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
                jsonObject.put("order_status", 4);
            }
        }catch (JSONException j){

        }

        orderPresenter.onOrder(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("page", 1);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
            if (myOrderGroupMenuIncomplete.isChecked()) {
                jsonObject.put("order_status", 3);
            } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
                jsonObject.put("order_status",4);
            }
        }catch (JSONException j){

        }

        orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @OnClick({R.id.top_bar_back, R.id.my_order_group_menu_incomplete, R.id.my_order_group_menu_recent_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.my_order_group_menu_incomplete:
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
                JSONObject incomplete=new JSONObject();
                try {
                    incomplete.put("appkey", Constants.ZSBAPPKEY);
                    incomplete.put("version", Constants.ZSBVERSION);
                    incomplete.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    incomplete.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    incomplete.put("order_status", 3);
                    incomplete.put("page", 1);
                    incomplete.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                }catch (JSONException j){

                }
                orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, incomplete);
                break;
            case R.id.my_order_group_menu_recent_completed:
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
                JSONObject recent_completed=new JSONObject();
                try {
                    recent_completed.put("appkey", Constants.ZSBAPPKEY);
                    recent_completed.put("version", Constants.ZSBVERSION);
                    recent_completed.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    recent_completed.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    recent_completed.put("order_status", 4);
                    recent_completed.put("page", 1);
                    recent_completed.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                }catch (JSONException j){

                }
                orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, recent_completed);
                break;
        }
    }

    @Override
    public void setConfirmPayView(confirmpayResponse response) {
        if (response.errno==0){
            showToast(response.offer_price);
            this.initViewsAndEvents();
        }else {
            showToast("订单异常");
        }

    }

    @Override
    public void showTip(String msg) {

    }
}
