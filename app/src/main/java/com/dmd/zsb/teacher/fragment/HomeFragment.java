package com.dmd.zsb.teacher.fragment;


import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.rollviewpager.RollPagerView;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.HomePresenterImpl;
import com.dmd.zsb.mvp.view.HomeView;
import com.dmd.zsb.protocol.response.homeResponse;
import com.dmd.zsb.protocol.table.DemandsBean;
import com.dmd.zsb.protocol.table.GradesBean;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.UserDetailActivity;
import com.dmd.zsb.teacher.activity.base.BaseFragment;
import com.dmd.zsb.teacher.adapter.HomeCarouselAdapter;
import com.dmd.zsb.teacher.adapter.SeekGradeAdapter;
import com.dmd.zsb.teacher.adapter.SeekSortAdapter;
import com.dmd.zsb.teacher.adapter.SeekSubjectAdapter;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeView, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.bar_home_address)
    TextView barHomeAddress;
    @Bind(R.id.bar_home_demand)
    TextView barHomeDemand;
    @Bind(R.id.fragment_home_list_view)
    LoadMoreListView fragmentHomeListView;
    @Bind(R.id.fragment_home_list_swipe_layout)
    XSwipeRefreshLayout fragmentHomeListSwipeLayout;
    @Bind(R.id.seek_group_menu_course)
    RadioButton seekGroupMenuCourse;
    @Bind(R.id.seek_group_menu_sort)
    RadioButton seekGroupMenuSort;
    @Bind(R.id.seek_group_menu_follow)
    RadioButton seekGroupMenuFollow;

    private View mHeaderView;
    private RollPagerView mRollPagerView;
    private ListViewDataAdapter<DemandsBean> mListViewAdapter;
    private int page = 1;
    private int sort = 0;
    private String subid = "";
    private HomePresenterImpl mHomePresenter = null;

    ListView seek_list_view_grade, seek_list_view_subject, seek_list_view_sort;

    private SeekGradeAdapter seekGradeAdapter;
    private SeekSubjectAdapter seekSubjectAdapter;
    private SeekSortAdapter seekSortAdapter;

    private int screenWidth;
    private int screenHeight;


    /**
     *
     */
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    /**
     * 第一次用户可见
     */
    @Override
    protected void onFirstUserVisible() {


    }

    /**
     * 用户可见
     */
    @Override
    protected void onUserVisible() {

    }

    /**
     * 用户不可见
     */
    @Override
    protected void onUserInvisible() {

    }

    /**
     * 加载视图的根视图
     */
    @Override
    protected View getLoadingTargetView() {
        return fragmentHomeListSwipeLayout;
    }

    /**
     * 初始化视图事件
     */
    @Override
    protected void initViewsAndEvents() {
        barHomeDemand.setVisibility(View.GONE);
        initScreenWidth();
        mHomePresenter = new HomePresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentHomeListSwipeLayout) {
                fragmentHomeListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //提交的参数封装
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("appkey", Constants.ZSBAPPKEY);
                            jsonObject.put("version", Constants.ZSBVERSION);
                            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                            jsonObject.put("page", 1);//页码
                            jsonObject.put("subid", subid);//科目id
                            jsonObject.put("sort", sort);//科目id
                            jsonObject.put("order_status", 0);//科目id
                        } catch (JSONException j) {

                        }

                        mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //提交的参数封装
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                        jsonObject.put("page", 1);//页码
                        jsonObject.put("subid", subid);//科目id
                        jsonObject.put("sort", sort);//科目id
                        jsonObject.put("order_status", 0);
                    } catch (JSONException j) {

                    }

                    mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }

        barHomeAddress.setText(XmlDB.getInstance(mContext).getKeyString("BDLocation", "定位"));

        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.tutor_home_list_header, null);
        mRollPagerView = (RollPagerView) ButterKnife.findById(mHeaderView, R.id.fragment_home_list_header_roll_view_pager);
        mListViewAdapter = new ListViewDataAdapter<DemandsBean>(new ViewHolderCreator<DemandsBean>() {

            @Override
            public ViewHolderBase<DemandsBean> createViewHolder(int position) {
                return new ViewHolderBase<DemandsBean>() {
                    TextView tv_oid,
                            tv_pid,
                            tv_location,
                            tv_offer_price,
                            tv_appointment_time,
                            tv_text,
                            tv_order_status,
                            tv_subid;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.tutor_demand_list_item, null);
                        tv_oid = ButterKnife.findById(view, R.id.tv_oid);
                        tv_pid = ButterKnife.findById(view, R.id.tv_pid);
                        tv_location = ButterKnife.findById(view, R.id.tv_location);
                        tv_offer_price = ButterKnife.findById(view, R.id.tv_offer_price);
                        tv_appointment_time = ButterKnife.findById(view, R.id.tv_appointment_time);
                        tv_text = ButterKnife.findById(view, R.id.tv_text);
                        tv_order_status = ButterKnife.findById(view, R.id.tv_order_status);
                        tv_subid = ButterKnife.findById(view, R.id.tv_subid);
                        return view;
                    }

                    @Override
                    public void showData(int position, DemandsBean itemData) {
                        tv_oid.setText(itemData.oid);
                        tv_pid.setText(itemData.pid);
                        tv_location.setText(itemData.location);
                        tv_offer_price.setText(itemData.offer_price);
                        tv_appointment_time.setText(itemData.appointment_time);
                        tv_text.setText(itemData.text);
                        if (itemData.order_status==0){
                            tv_order_status.setText("征集中");
                        }else if (itemData.order_status==1){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==2){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==3){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==4){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==5){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==6){
                            tv_order_status.setText("");
                        }

                        tv_subid.setText(itemData.subid);
                        //teacher_distance.setText(LocationManager.getDistance(Double.parseDouble(itemData.lat), Double.parseDouble(itemData.lon)));
                    }
                };
            }
        });

        //TODO 数据适配
        seekGroupMenuCourse.setChecked(true);
        if (fragmentHomeListView.getHeaderViewsCount() == 0)
            fragmentHomeListView.addHeaderView(mHeaderView);
        fragmentHomeListView.setAdapter(mListViewAdapter);
        fragmentHomeListView.setOnItemClickListener(this);
        fragmentHomeListView.setOnLoadMoreListener(this);

        fragmentHomeListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentHomeListSwipeLayout.setOnRefreshListener(this);

    }

    /**
     * 绑定事件 true
     */
    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * 接受事件
     */
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }
//===============================HomeView============================================

    @Override
    public void navigateToUserDetail(DemandsBean itemData) {
        if (itemData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("details", itemData);
            readyGo(UserDetailActivity.class, bundle);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListViewAdapter != null) {
            int j = position + -1;
            if (j >= 0 && j < mListViewAdapter.getDataList().size()) {
                DemandsBean data = (DemandsBean) parent.getItemAtPosition(position);
                navigateToUserDetail(data);
            }
        }
    }

    @Override
    public void refreshListData(homeResponse response) {
        if (fragmentHomeListSwipeLayout != null)
            fragmentHomeListSwipeLayout.setRefreshing(false);
        if (response != null) {
            if (response.demands.size() >= 1) {//用户列表
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.demands);
                    mListViewAdapter.notifyDataSetChanged();
                }
            } else {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
            }
            if (response.advertisements.size() >= 1) {//广告
                mRollPagerView.setAdapter(new HomeCarouselAdapter(mContext, response.advertisements));
            }
            if (fragmentHomeListView != null) {
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page) {
                    fragmentHomeListView.setCanLoadMore(true);
                } else {
                    fragmentHomeListView.setCanLoadMore(false);
                }
            }
        }
    }

    @Override
    public void addMoreListData(homeResponse response) {
        if (fragmentHomeListView != null)
            fragmentHomeListView.onLoadMoreComplete();
        if (response != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(response.demands);
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentHomeListView != null) {
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page) {
                    fragmentHomeListView.setCanLoadMore(true);
                } else {
                    fragmentHomeListView.setCanLoadMore(false);
                }
            }
        }
    }

    //==============================LoadMoreListView.OnLoadMoreListener=============================================
    @Override
    public void onLoadMore() {
        page = 1 + page;
        //提交的参数封装
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
            jsonObject.put("page", page);//页码
            jsonObject.put("subid", subid);//科目id
            jsonObject.put("sort", sort);//科目id
            jsonObject.put("order_status", 0);

        } catch (JSONException j) {

        }

        mHomePresenter.loadListData(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }
    //==============================SwipeRefreshLayout.OnRefreshListener=============================================


    @Override
    public void onRefresh() {

        //提交的参数封装
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
            jsonObject.put("page", 1);//页码
            jsonObject.put("subid", subid);//科目id
            jsonObject.put("sort", sort);//科目id
            jsonObject.put("order_status", 0);
        } catch (JSONException j) {

        }

        mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    //排序
    public void onCreateSortPopWindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.seek_menu_sort_popwindow, null);
        seek_list_view_sort = (ListView) contentView.findViewById(R.id.seek_list_view_sort);
        String[] strings = getActivity().getResources().getStringArray(R.array.sort_category_list);
        seekSortAdapter = new SeekSortAdapter(mContext, strings);
        seek_list_view_sort.setAdapter(seekSortAdapter);
        seek_list_view_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //排序
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("page", 1);
                    jsonObject.put("subid", subid);//科目id
                    jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                    jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    //jsonObject.put("","");
                    if (parent.getAdapter().getItem(position).toString().equals("综合排序")) {
                        sort = 0;
                    } else if (parent.getAdapter().getItem(position).toString().equals("价钱 低--高")) {
                        sort = 1;
                    } else if (parent.getAdapter().getItem(position).toString().equals("价钱 高--低")) {
                        sort = 2;
                    } else if (parent.getAdapter().getItem(position).toString().equals("人气 高--低")) {
                        sort = 3;
                    } else if (parent.getAdapter().getItem(position).toString().equals("距离 近--远")) {
                        sort = 4;
                    }
                    jsonObject.put("sort", sort);//科目id
                    jsonObject.put("order_status", 0);
                } catch (JSONException j) {

                }

                mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
                popupWindow.dismiss();
            }
        });
        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(view);

    }

    //课程
    public void onCreateCoursePopWindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.seek_menu_class_popupwindow, null);
        seek_list_view_grade = (ListView) contentView.findViewById(R.id.seek_list_view_grade);
        seek_list_view_subject = (ListView) contentView.findViewById(R.id.seek_list_view_subject);

        if (seek_list_view_grade.getVisibility() == View.INVISIBLE) {
            seek_list_view_grade.setVisibility(View.VISIBLE);
        }
        seekGradeAdapter = new SeekGradeAdapter(GradesBean.listAll(GradesBean.class), mContext);
        seek_list_view_grade.setAdapter(seekGradeAdapter);
        seek_list_view_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getAdapter() instanceof SeekGradeAdapter) {

                    if (seek_list_view_subject.getVisibility() == View.INVISIBLE) {
                        seek_list_view_subject.setVisibility(View.VISIBLE);
                    }

                    if (!SubjectsBean.find(SubjectsBean.class, "GRADEID=?", GradesBean.listAll(GradesBean.class).get(position).grade_id).isEmpty()) {
                        seekSubjectAdapter = new SeekSubjectAdapter(SubjectsBean.find(SubjectsBean.class, "GRADEID=?", GradesBean.listAll(GradesBean.class).get(position).grade_id), mContext);
                        seek_list_view_subject.setAdapter(seekSubjectAdapter);
                        seekSubjectAdapter.notifyDataSetChanged();
                        seek_list_view_subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //请求数据
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    sort = 0;
                                    subid = ((SubjectsBean) parent.getAdapter().getItem(position)).sub_id;
                                    jsonObject.put("appkey", Constants.ZSBAPPKEY);
                                    jsonObject.put("version", Constants.ZSBVERSION);
                                    jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                                    jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                                    jsonObject.put("page", 1);
                                    jsonObject.put("subid", subid);//科目id
                                    jsonObject.put("sort", sort);//科目id
                                    jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                                    jsonObject.put("order_status", 0);
                                } catch (JSONException j) {

                                }
                                mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
                                popupWindow.dismiss();
                            }
                        });
                    }
                }
            }
        });


        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(screenHeight * 4);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(view);
    }

    private void initScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels / 10;
        screenWidth = dm.widthPixels;
    }


    @OnClick({R.id.seek_group_menu_course, R.id.seek_group_menu_sort, R.id.seek_group_menu_follow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seek_group_menu_course:
                if (seekGroupMenuCourse.isChecked()) {
                    onCreateCoursePopWindow(seekGroupMenuCourse);
                }
                break;
            case R.id.seek_group_menu_sort:
                if (seekGroupMenuSort.isChecked()) {
                    onCreateSortPopWindow(seekGroupMenuSort);
                }
                break;
            case R.id.seek_group_menu_follow:
                if (seekGroupMenuFollow.isChecked()){
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.put("page", 1);
                        jsonObject.put("subid", subid);//科目id
                        jsonObject.put("sort", 100);//科目id
                        jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                    }catch (JSONException j){

                    }
                    mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
                }
                break;
        }
    }
}
