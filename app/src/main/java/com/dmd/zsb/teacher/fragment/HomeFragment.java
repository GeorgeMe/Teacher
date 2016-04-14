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
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.rollviewpager.RollPagerView;
import com.dmd.tutor.utils.TLog;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.db.dao.GradeDao;
import com.dmd.zsb.db.dao.SubjectDao;
import com.dmd.zsb.entity.GradeEntity;
import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.entity.SubjectEntity;
import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.mvp.presenter.HomePresenter;
import com.dmd.zsb.mvp.presenter.impl.HomePresenterImpl;
import com.dmd.zsb.mvp.view.HomeView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.OrderDetailActivity;
import com.dmd.zsb.teacher.activity.base.BaseFragment;
import com.dmd.zsb.teacher.adapter.HomeCarouselAdapter;
import com.dmd.zsb.teacher.adapter.SeekGradeAdapter;
import com.dmd.zsb.teacher.adapter.SeekSortAdapter;
import com.dmd.zsb.teacher.adapter.SeekSubjectAdapter;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeView, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.bar_home_address)
    TextView barHomeAddress;
    @Bind(R.id.seek_group_menu_course)
    RadioButton seekGroupMenuCourse;
    @Bind(R.id.seek_group_menu_sort)
    RadioButton seekGroupMenuSort;
    @Bind(R.id.seek_group_menu_audition)
    RadioButton seekGroupMenuAudition;
    @Bind(R.id.fragment_home_list_view)
    LoadMoreListView fragmentHomeListView;
    @Bind(R.id.fragment_home_list_swipe_layout)
    XSwipeRefreshLayout fragmentHomeListSwipeLayout;

    private int page=1;
    private HomePresenter mHomePresenter=null;
    private ListViewDataAdapter<OrderEntity> mListViewAdapter;
    private RollPagerView mRollPagerView;
    private View mHeaderView;
    private ListView seek_list_view_grade, seek_list_view_subject, seek_list_view_sort;
    private SeekGradeAdapter seekGradeAdapter;
    private SeekSubjectAdapter seekSubjectAdapter;
    private SeekSortAdapter seekSortAdapter;

    private int screenWidth;
    private int screenHeight;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onFirstUserVisible() {
        mHomePresenter = new HomePresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentHomeListSwipeLayout) {
                fragmentHomeListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject jsonObject=new JsonObject();
                        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                        jsonObject.addProperty("sid",XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                        jsonObject.addProperty("page",1);//页码
                        jsonObject.addProperty("subid","subid");//科目id
                        jsonObject.addProperty("group_menu","demandLevyConcentration");//
                        mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("uid",XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                    jsonObject.addProperty("sid",XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                    jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                    jsonObject.addProperty("page",1);//页码
                    jsonObject.addProperty("subid","subid");//科目id
                    jsonObject.addProperty("group_menu","demandLevyConcentration");//
                    mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
                }
            });
        }
        LocationManager.getInstance().refreshLocation();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentHomeListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        initScreenWidth();
        seekGroupMenuCourse.setChecked(true);
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.tutor_home_list_header, null);
        mRollPagerView = (RollPagerView) ButterKnife.findById(mHeaderView, R.id.fragment_home_list_header_roll_view_pager);

        mListViewAdapter = new ListViewDataAdapter<OrderEntity>(new ViewHolderCreator<OrderEntity>() {
            @Override
            public ViewHolderBase<OrderEntity> createViewHolder(int position) {
                return new ViewHolderBase<OrderEntity>() {

                    TextView tv_type,tv_sex,tv_appointed_time, tv_charging, tv_curriculum, tv_address, tv_place, tv_praise;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.demand_list_item, null);
                        tv_type = ButterKnife.findById(view, R.id.tv_type);
                        tv_sex = ButterKnife.findById(view, R.id.tv_sex);
                        tv_appointed_time = ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging = ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum = ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_address = ButterKnife.findById(view, R.id.tv_address);
                        tv_place = ButterKnife.findById(view, R.id.tv_place);
                        tv_praise = ButterKnife.findById(view, R.id.tv_praise);
                        return view;
                    }

                    @Override
                    public void showData(int position, OrderEntity itemData) {

                        tv_type.setText(itemData.getType());
                        tv_sex.setText(itemData.getSex());
                        tv_praise.setText(itemData.getPraise());
                        tv_appointed_time.setText(itemData.getAppointed_time());
                        tv_charging.setText(itemData.getCharging());
                        tv_curriculum.setText(itemData.getCurriculum());
                        tv_address.setText(itemData.getAddress());
                        tv_place.setText(itemData.getPlace());

                    }
                };
            }
        });
        //TODO 数据适配

        if (fragmentHomeListView.getHeaderViewsCount() == 0)
            fragmentHomeListView.addHeaderView(mHeaderView);

        fragmentHomeListView.setAdapter(mListViewAdapter);
        fragmentHomeListView.setOnLoadMoreListener(this);
        fragmentHomeListView.setOnItemClickListener(this);

        fragmentHomeListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentHomeListSwipeLayout.setOnRefreshListener(this);

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public void navigateToOrderDetail(OrderEntity data) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",data);
        readyGo(OrderDetailActivity.class,bundle);
    }

    @Override
    public void refreshListData(HomeResponse data) {
        if (fragmentHomeListSwipeLayout != null)
            fragmentHomeListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getOrderEntities().size() >= 0) {//用户列表
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getOrderEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getAdvertisements().size() >= 2) {//广告
                mRollPagerView.setAdapter(new HomeCarouselAdapter(mContext, data.getAdvertisements()));
            }
            if (data.getTotal_page() > page)
                fragmentHomeListView.setCanLoadMore(true);
            else
                fragmentHomeListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(HomeResponse data) {
        if (fragmentHomeListView != null)
            fragmentHomeListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getOrderEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page()> page)
                fragmentHomeListView.setCanLoadMore(true);
            else
                fragmentHomeListView.setCanLoadMore(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListViewAdapter != null) {
            int j = position + -1;
            if (j >= 0 && j < mListViewAdapter.getDataList().size()){
                OrderEntity orderEntity = (OrderEntity) parent.getItemAtPosition(position);
                navigateToOrderDetail(orderEntity);
            }
        }
    }

    @Override
    public void onLoadMore() {
        page = 1 + page;
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("uid",XmlDB.getInstance(mContext).getKeyString("uid","uid"));
        jsonObject.addProperty("sid",XmlDB.getInstance(mContext).getKeyString("sid","sid"));
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
        jsonObject.addProperty("page",page);//页码
        jsonObject.addProperty("subid","subid");//科目id
        jsonObject.addProperty("group_menu","demandLevyConcentration");//
        mHomePresenter.loadListData(Constants.EVENT_LOAD_MORE_DATA,jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("uid",XmlDB.getInstance(mContext).getKeyString("uid","uid"));
        jsonObject.addProperty("sid",XmlDB.getInstance(mContext).getKeyString("sid","sid"));
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
        jsonObject.addProperty("page",1);//页码
        jsonObject.addProperty("subid","subid");//科目id
        jsonObject.addProperty("group_menu","demandLevyConcentration");//
        mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
    }

    @OnClick({R.id.seek_group_menu_course, R.id.seek_group_menu_sort, R.id.seek_group_menu_audition})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seek_group_menu_course:
                if (seekGroupMenuCourse.isChecked()){
                    onCreateCoursePopWindow(seekGroupMenuCourse);
                }
                break;
            case R.id.seek_group_menu_sort:
                if (seekGroupMenuSort.isChecked()){
                    onCreateSortPopWindow(seekGroupMenuSort);
                }
                break;
            case R.id.seek_group_menu_audition:
                showToast("再做一下");
                break;
        }
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
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("uid",XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                jsonObject.addProperty("sid",XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                jsonObject.addProperty("page",page);//页码
                jsonObject.addProperty("sort","sort");//排序规则
                jsonObject.addProperty("subid", parent.getAdapter().getItem(position).toString());//科目ID
                jsonObject.addProperty("group_menu","demandLevyConcentration");//

                mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
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
        seekGradeAdapter = new SeekGradeAdapter(getGrades(), mContext);
        seek_list_view_grade.setAdapter(seekGradeAdapter);
        seek_list_view_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getAdapter() instanceof SeekGradeAdapter) {

                    if (seek_list_view_subject.getVisibility() == View.INVISIBLE) {
                        seek_list_view_subject.setVisibility(View.VISIBLE);
                    }

                    if (!getSubjects(getGrades().get(position).getGrade_id()).isEmpty()) {
                        seekSubjectAdapter = new SeekSubjectAdapter(getSubjects(getGrades().get(position).getGrade_id()), mContext);
                        seek_list_view_subject.setAdapter(seekSubjectAdapter);
                        seekSubjectAdapter.notifyDataSetChanged();
                        seek_list_view_subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //请求数据
                                JsonObject jsonObject=new JsonObject();
                                jsonObject.addProperty("uid",XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                                jsonObject.addProperty("sid",XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                                jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                                jsonObject.addProperty("page",page);//页码
                                jsonObject.addProperty("sort","sort");//排序规则
                                jsonObject.addProperty("group_menu","demandLevyConcentration");//
                                jsonObject.addProperty("subid", parent.getAdapter().getItem(position).toString());

                                mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
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
        TLog.v("屏幕宽高", "宽度" + screenWidth + "高度" + screenHeight);
    }

    private List<GradeEntity> getGrades() {
        GradeDao gradeDao = new GradeDao(ZSBDataBase.getInstance(mContext));
        return gradeDao.getGrades();
    }

    private List<SubjectEntity> getSubjects(String grade_id) {
        SubjectDao subjectDao = new SubjectDao(ZSBDataBase.getInstance(mContext));
        return subjectDao.getGrades(grade_id);
    }
}
