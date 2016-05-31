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
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.EvaluationPresenterImpl;
import com.dmd.zsb.mvp.view.EvaluationView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.evaluationResponse;
import com.dmd.zsb.protocol.table.EvaluationsBean;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationActivity extends BaseActivity implements EvaluationView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    @Bind(R.id.evaluation_menu_group)
    RadioGroup evaluationMenuGroup;
    @Bind(R.id.fragment_evaluation_list_list_view)
    LoadMoreListView fragmentEvaluationListListView;
    @Bind(R.id.fragment_evaluation_list_swipe_layout)
    XSwipeRefreshLayout fragmentEvaluationListSwipeLayout;
    @Bind(R.id.evaluation_group_menu_incomplete)
    RadioButton evaluationGroupMenuIncomplete;
    @Bind(R.id.evaluation_group_menu_recent_completed)
    RadioButton evaluationGroupMenuRecentCompleted;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;

    private EvaluationPresenterImpl evaluationPresenter;
    private ListViewDataAdapter<EvaluationsBean> mListViewAdapter;
    private int page = 1;

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_evaluation;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentEvaluationListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("评价");
        evaluationGroupMenuIncomplete.setChecked(true);
        evaluationPresenter = new EvaluationPresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentEvaluationListSwipeLayout) {
                fragmentEvaluationListSwipeLayout.postDelayed(new Runnable() {
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
                            if (evaluationGroupMenuIncomplete.isChecked()) {
                                jsonObject.put("flag", 5);
                            } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                                jsonObject.put("flag", 6);
                            }
                        }catch (JSONException j){

                        }

                        evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, jsonObject);
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
                        if (evaluationGroupMenuIncomplete.isChecked()) {
                            jsonObject.put("flag", 5);
                        } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                            jsonObject.put("flag", 6);
                        }
                    }catch (JSONException j){

                    }

                    evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }
        mListViewAdapter = new ListViewDataAdapter<EvaluationsBean>(new ViewHolderCreator<EvaluationsBean>() {


            @Override
            public ViewHolderBase<EvaluationsBean> createViewHolder(int position) {


                return new ViewHolderBase<EvaluationsBean>() {
                    ImageView img_header;
                    TextView tv_name, tv_type, tv_sex, tv_appointed_time, tv_charging, tv_curriculum, tv_note;

                    //定义UI控件
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        // 实例化UI控件
                        View view = layoutInflater.inflate(R.layout.evaluation_list_item, null);
                        img_header = ButterKnife.findById(view, R.id.img_header);
                        tv_name = ButterKnife.findById(view, R.id.tv_name);
                        tv_type = ButterKnife.findById(view, R.id.tv_type);
                        tv_sex = ButterKnife.findById(view, R.id.tv_sex);
                        tv_appointed_time = ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging = ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum = ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_note = ButterKnife.findById(view, R.id.tv_note);
                        return view;
                    }

                    @Override
                    public void showData(int position, EvaluationsBean itemData) {
                        //数据展示set
                        if (evaluationGroupMenuIncomplete.isChecked()) {
                            tv_note.setVisibility(View.GONE);
                        } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                            tv_note.setVisibility(View.VISIBLE);
                            tv_note.setText(itemData.note);
                        }
                        Picasso.with(mContext).load(itemData.img_header).into(img_header);
                        tv_name.setText(itemData.name);
                        tv_type.setText(itemData.type);
                        tv_sex.setText(itemData.sex);
                        tv_appointed_time.setText(itemData.appointed_time);
                        tv_charging.setText(itemData.charging);
                        tv_curriculum.setText(itemData.curriculum);

                    }
                };
            }
        });
        fragmentEvaluationListListView.setAdapter(mListViewAdapter);
        fragmentEvaluationListListView.setOnItemClickListener(this);
        fragmentEvaluationListListView.setOnLoadMoreListener(this);

        fragmentEvaluationListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentEvaluationListSwipeLayout.setOnRefreshListener(this);

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
    public void navigateToEvaluationDetail(EvaluationsBean itemData) {
        if (itemData!=null)
        showToast("评论"+itemData.appointed_time);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EvaluationsBean evaluationsBean = (EvaluationsBean) parent.getAdapter().getItem(position);
        navigateToEvaluationDetail(evaluationsBean);
    }

    @Override
    public void onLoadMore() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("page", page);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
            if (evaluationGroupMenuIncomplete.isChecked()) {
                jsonObject.put("flag", 5);
            } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                jsonObject.put("flag", 6);
            }
        }catch (JSONException j){

        }

        evaluationPresenter.onEvaluation(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("page", page);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
            if (evaluationGroupMenuIncomplete.isChecked()) {
                jsonObject.put("flag", 5);
            } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                jsonObject.put("flag", 6);
            }
        }catch (JSONException j){

        }
        evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @Override
    public void refreshListData(evaluationResponse response) {
        if (fragmentEvaluationListSwipeLayout != null)
            fragmentEvaluationListSwipeLayout.setRefreshing(false);
        if (response != null) {
            if (response.evaluations.size() >= 1) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.evaluations);
                    mListViewAdapter.notifyDataSetChanged();
                }
            }else {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                fragmentEvaluationListListView.setCanLoadMore(true);
            else
                fragmentEvaluationListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(evaluationResponse response) {
        if (fragmentEvaluationListListView != null)
            fragmentEvaluationListListView.onLoadMoreComplete();
        if (response != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(response.evaluations);
                mListViewAdapter.notifyDataSetChanged();
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count)> page)
                fragmentEvaluationListListView.setCanLoadMore(true);
            else
                fragmentEvaluationListListView.setCanLoadMore(false);
        }
    }


    @OnClick({R.id.top_bar_back,R.id.evaluation_group_menu_incomplete, R.id.evaluation_group_menu_recent_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.evaluation_group_menu_incomplete:
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
                JSONObject evaluation_group_menu_incomplete=new JSONObject();
                try {
                    evaluation_group_menu_incomplete.put("appkey", Constants.ZSBAPPKEY);
                    evaluation_group_menu_incomplete.put("version", Constants.ZSBVERSION);
                    evaluation_group_menu_incomplete.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    evaluation_group_menu_incomplete.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    evaluation_group_menu_incomplete.put("page", 1);
                    evaluation_group_menu_incomplete.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    evaluation_group_menu_incomplete.put("flag", 5);
                }catch (JSONException j){

                }

                evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, evaluation_group_menu_incomplete);
                break;
            case R.id.evaluation_group_menu_recent_completed:

                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
                JSONObject evaluation_group_menu_recent_completed=new JSONObject();
                try {
                    evaluation_group_menu_recent_completed.put("appkey", Constants.ZSBAPPKEY);
                    evaluation_group_menu_recent_completed.put("version", Constants.ZSBVERSION);
                    evaluation_group_menu_recent_completed.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    evaluation_group_menu_recent_completed.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    evaluation_group_menu_recent_completed.put("page", 1);
                    evaluation_group_menu_recent_completed.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    evaluation_group_menu_recent_completed.put("flag", 6);
                }catch (JSONException j){

                }

                evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, evaluation_group_menu_recent_completed);
                break;
        }
    }

}
