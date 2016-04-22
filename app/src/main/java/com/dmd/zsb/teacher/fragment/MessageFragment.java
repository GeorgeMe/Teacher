package com.dmd.zsb.teacher.fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.conversation.IYWConversationListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.fundamental.widget.refreshlist.YWPullToRefreshBase;
import com.alibaba.mobileim.fundamental.widget.refreshlist.YWPullToRefreshListView;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseFragment;
import com.dmd.zsb.teacher.adapter.ConversationListAdapter;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.message_group_menu_message)
    RadioButton messageGroupMenuMessage;
    @Bind(R.id.message_group_menu_recent_contacts)
    RadioButton messageGroupMenuRecentContacts;
    @Bind(R.id.message_group_menu_attention)
    RadioButton messageGroupMenuAttention;
    @Bind(R.id.message_menu_group)
    RadioGroup messageMenuGroup;

    private YWIMCore mIMCore;
    private IYWConversationService mConversationService;
    private List<YWConversation> mConversationList;
    private ConversationListAdapter mAdapter;

    private YWPullToRefreshListView mPullToRefreshListView;
    private ListView mListView;

    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onFirstUserVisible() {
        mPullToRefreshListView = (YWPullToRefreshListView) ButterKnife.findById(getActivity(),R.id.conversation_list);
        mPullToRefreshListView.setMode(YWPullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
        mPullToRefreshListView.setShowIndicator(false);
        mPullToRefreshListView.setDisableScrollingWhileRefreshing(false);
        mPullToRefreshListView.setRefreshingLabel("同步群成员列表");
        mPullToRefreshListView.setReleaseLabel("松开同步群成员列表");
        mPullToRefreshListView.setDisableRefresh(false);
        mPullToRefreshListView.setOnRefreshListener(new YWPullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncRecentConversations();
            }
        });
        mListView = mPullToRefreshListView.getRefreshableView();
        mIMCore = YWAPI.createIMCore();
        mConversationService = mIMCore.getConversationService();
        //初始化最近联系人列表
        mConversationList = mConversationService.getConversationList();
        //初始化最近联系人adpter
        mAdapter = new ConversationListAdapter(mConversationList);
        //设置mListView的adapter
        mListView.setAdapter(mAdapter);

        //添加会话列表变更监听，收到该监听回调时更新adapter就可以刷新页面了
        mConversationService.addConversationListener(mConversationListener);
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
        messageGroupMenuMessage.setChecked(true);
        messageGroupMenuMessage.setOnClickListener(this);
        messageGroupMenuRecentContacts.setOnClickListener(this);
        messageGroupMenuAttention.setOnClickListener(this);
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
        if (messageGroupMenuMessage==v){

        }else if (messageGroupMenuRecentContacts==v){

        }else if (messageGroupMenuAttention==v){

        }
    }

    IYWConversationListener mConversationListener = new IYWConversationListener() {
        @Override
        public void onItemUpdated() {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChangedWithAsyncLoad();
                }
            });
        }
    };
    private void syncRecentConversations(){
        mConversationService.syncRecentConversations(new IWxCallback() {
            @Override
            public void onSuccess(Object... result) {
                mUIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChangedWithAsyncLoad();
                        mPullToRefreshListView.onRefreshComplete(false, true);
                    }
                });
            }

            @Override
            public void onError(int code, String info) {
                mUIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshListView.onRefreshComplete(false, false);
                    }
                });
            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //请务必在该方法中移除会话监听，以免多次添加监听
        if (mConversationService != null){
            mConversationService.removeConversationListener(mConversationListener);
        }
    }
}
