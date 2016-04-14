package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.entity.response.OrderResponse;
import com.dmd.zsb.mvp.presenter.impl.OrderPresenterImpl;
import com.dmd.zsb.mvp.view.OrderView;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity implements OrderView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

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
    private ListViewDataAdapter<OrderEntity> mListViewAdapter;
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
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.addProperty("page", page);
                        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                        if (myOrderGroupMenuIncomplete.isChecked()) {
                            jsonObject.addProperty("order_status", 1);
                        } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
                            jsonObject.addProperty("order_status", 4);
                        } else {
                            jsonObject.addProperty("order_status", 1);
                        }
                        orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.addProperty("page", page);
                    jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                    if (myOrderGroupMenuIncomplete.isChecked()) {
                        jsonObject.addProperty("order_status", 1);
                    } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
                        jsonObject.addProperty("order_status", 4);
                    } else {
                        jsonObject.addProperty("order_status", 1);
                    }
                    orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }
        mListViewAdapter = new ListViewDataAdapter<OrderEntity>(new ViewHolderCreator<OrderEntity>() {
            @Override
            public ViewHolderBase<OrderEntity> createViewHolder(int position) {
                return new ViewHolderBase<OrderEntity>() {
                    ImageView img_header;
                    TextView tv_name, tv_type, tv_sex, tv_appointed_time, tv_charging, tv_curriculum, tv_address, tv_place, tv_state;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.order_list_item, null);
                        img_header = ButterKnife.findById(view, R.id.img_header);
                        tv_name = ButterKnife.findById(view, R.id.tv_name);
                        tv_type = ButterKnife.findById(view, R.id.tv_type);
                        tv_sex = ButterKnife.findById(view, R.id.tv_sex);
                        tv_appointed_time = ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging = ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum = ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_address = ButterKnife.findById(view, R.id.tv_address);
                        tv_place = ButterKnife.findById(view, R.id.tv_place);
                        tv_state = ButterKnife.findById(view, R.id.tv_state);
                        return view;
                    }

                    @Override
                    public void showData(int position, OrderEntity itemData) {
                        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.getImg_header()).into(img_header);
                        tv_name.setText(itemData.getName());
                        tv_type.setText(itemData.getType());
                        tv_sex.setText(itemData.getSex());
                        tv_appointed_time.setText(itemData.getAppointed_time());
                        tv_charging.setText(itemData.getCharging());
                        tv_curriculum.setText(itemData.getCurriculum());
                        tv_address.setText(itemData.getAddress());
                        tv_place.setText(itemData.getPlace());

                        if (itemData.getState().equals("1")) {
                            tv_state.setText("待完成");
                        } else if (itemData.getState().equals("3")) {
                            tv_state.setText("未付款");
                        } else if (itemData.getState().equals("4")) {
                            tv_state.setText("已付款");
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
    public void navigateToOrderDetail(OrderEntity data) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",data);
        readyGo(OrderDetailActivity.class,bundle);
    }

    @Override
    public void refreshListData(OrderResponse data) {
        if (fragmentMyOrderListSwipeLayout != null)
            fragmentMyOrderListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getOrderEntities().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getOrderEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                fragmentMyOrderListListView.setCanLoadMore(true);
            else
                fragmentMyOrderListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(OrderResponse data) {
        if (fragmentMyOrderListListView != null)
            fragmentMyOrderListListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getOrderEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
                fragmentMyOrderListListView.setCanLoadMore(true);
            else
                fragmentMyOrderListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderEntity orderEntity = (OrderEntity) parent.getItemAtPosition(position);
        navigateToOrderDetail(orderEntity);
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        if (myOrderGroupMenuIncomplete.isChecked()) {
            jsonObject.addProperty("order_status", 1);
        } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
            jsonObject.addProperty("order_status", 4);
        } else {
            jsonObject.addProperty("order_status", 1);
        }
        orderPresenter.onOrder(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", 1);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        if (myOrderGroupMenuIncomplete.isChecked()) {
            jsonObject.addProperty("order_status", 1);
        } else if (myOrderGroupMenuRecentCompleted.isChecked()) {
            jsonObject.addProperty("order_status", 4);
        } else {
            jsonObject.addProperty("order_status", 1);
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
                JsonObject incomplete = new JsonObject();
                incomplete.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                incomplete.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                incomplete.addProperty("order_status", 1);
                incomplete.addProperty("page", 1);
                incomplete.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, incomplete);
                break;
            case R.id.my_order_group_menu_recent_completed:
                JsonObject recent_completed = new JsonObject();
                recent_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                recent_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                recent_completed.addProperty("order_status", 4);
                recent_completed.addProperty("page", 1);
                recent_completed.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA, recent_completed);
                break;
        }
    }

}
