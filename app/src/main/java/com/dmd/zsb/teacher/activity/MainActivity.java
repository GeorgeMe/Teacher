package com.dmd.zsb.teacher.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;
import com.alibaba.mobileim.contact.IYWContactOperateNotifyListener;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.IYWConversationUnreadChangeListener;
import com.alibaba.mobileim.conversation.IYWMessageLifeCycleListener;
import com.alibaba.mobileim.conversation.IYWSendMessageToContactInBlackListListener;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.conversation.YWMessageType;
import com.alibaba.mobileim.conversation.YWPushInfo;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.MainViewImpl;
import com.dmd.zsb.mvp.view.MainView;
import com.dmd.zsb.openim.LoginHelper;
import com.dmd.zsb.openim.Notification;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.teacher.fragment.HomeFragment;
import com.dmd.zsb.teacher.fragment.MessageFragment;
import com.dmd.zsb.teacher.fragment.MineFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView,TabHost.OnTabChangeListener {
    private final static String TAG=MainActivity.TAG_LOG;
    @Bind(android.R.id.tabhost)
    FragmentTabHost tabhost;

    public static final String LOGIN_SUCCESS = "loginSuccess";
    public static final String SYSTEM_TRIBE_CONVERSATION="sysTribe";
    public static final String SYSTEM_FRIEND_REQ_CONVERSATION="sysfrdreq";

    private static long DOUBLE_CLICK_TIME = 0L;

    public static final String TAB_HOME = "home";
    public static final String TAB_MESSAGE = "message";
    //public static final String TAB_SEEK = "seek";
    public static final String TAB_MINE = "mine";

    private TextView mHomeTab,mMessageTab,mSeekTab,mMineTab,mUnread;
    private Drawable mHomePressed,mHomeNormal,mMessagePressed,mMessageNormal;
    private Drawable mSeekPressed,mSeekNormal,mMinePressed,mMineNormal;

    private YWIMKit mIMKit;
    private IYWConversationService mConversationService;
   private IYWConversationUnreadChangeListener mConversationUnreadChangeListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private IYWMessageLifeCycleListener mMessageLifeCycleListener;
    private IYWSendMessageToContactInBlackListListener mSendMessageToContactInBlackListListener;
    private IYWContactOperateNotifyListener mContactOperateNotifyListener;
    private IYWContactCacheUpdateListener mContactCacheUpdateListener;



    private MainViewImpl mainView;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                showToast(getString(R.string.double_click_exit));
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                getBaseApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        return R.layout.activity_main;
    }
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode()== Constants.EVENT_RECOMMEND_COURSES_HOME){
            //tabhost.onTabChanged(TAB_SEEK);
            //tabhost.setCurrentTabByTag(TAB_SEEK);
            SubjectsBean entity=(SubjectsBean) eventCenter.getData();
            //BusHelper.post(new EventCenter(Constants.EVENT_RECOMMEND_COURSES_SEEK,entity.sub_id));
            XmlDB.getInstance(mContext).saveKey("subid",entity.sub_id);
        }else if (eventCenter.getEventCode()== Constants.EVENT_RECOMMEND_COURSES_SIGNIN){

        }
    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(this,R.id.realtabcontent);
    }

    @Override
    protected void initViewsAndEvents() {


        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            LoginHelper.getInstance().initSDK_Sample(getBaseApplication());
            mIMKit = LoginHelper.getInstance().getIMKit();
            if (mIMKit == null) {
                return;
            }
            mConversationService = mIMKit.getConversationService();
            initListeners();
        }

        mainView=new MainViewImpl(this,this);
        mainView.initialized();

        LocationManager.getInstance().refreshLocation();
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
        return true;
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
    public void initTabView() {
        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabhost.getTabWidget().setDividerDrawable(null);

        View indicator = getIndicatorView(TAB_HOME);
        tabhost.addTab(tabhost.newTabSpec(TAB_HOME).setIndicator(indicator),HomeFragment.class, null);

        indicator = getIndicatorView(TAB_MESSAGE);
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
          tabhost.addTab(tabhost.newTabSpec(TAB_MESSAGE).setIndicator(indicator), mIMKit.getConversationFragmentClass(), null);
        }else{
            tabhost.addTab(tabhost.newTabSpec(TAB_MESSAGE).setIndicator(indicator), MessageFragment.class, null);
        }

       // indicator = getIndicatorView(TAB_SEEK);
       // tabhost.addTab(tabhost.newTabSpec(TAB_SEEK).setIndicator(indicator), SeekFragment.class, null);

        indicator = getIndicatorView(TAB_MINE);
        tabhost.addTab(tabhost.newTabSpec(TAB_MINE).setIndicator(indicator), MineFragment.class, null);

        tabhost.setOnTabChangedListener(this);
        this.onTabChanged(TAB_HOME);
    }

    @Override
    public View getIndicatorView(String tab) {
        View tabView = View.inflate(this, R.layout.tutor_tab_item, null);
        TextView indicator = (TextView) tabView.findViewById(R.id.tab_text);
        Drawable drawable;

        if (tab.equals(TAB_HOME)) {
            indicator.setText("主页");
            drawable = getResources().getDrawable(R.drawable.nav_home_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mHomeTab = indicator;
        } else if (tab.equals(TAB_MESSAGE)) {
            indicator.setText("消息");
            mUnread = (TextView) tabView.findViewById(R.id.unread);
            drawable = getResources().getDrawable(R.drawable.nav_message_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMessageTab = indicator;
        }
/*        else if (tab.equals(TAB_SEEK)) {
            indicator.setText("找老师");
            drawable = getResources().getDrawable(R.drawable.nav_seek_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mSeekTab = indicator;
        } */
        else if (tab.equals(TAB_MINE)) {
            indicator.setText("我的");
            drawable = getResources().getDrawable(R.drawable.nav_message_normal);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            indicator.setCompoundDrawables(null, drawable, null, null);
            mMineTab = indicator;
        }
        return tabView;
    }

    @Override
    public void setHomeText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mHomeTab.setTextColor(getResources().getColor(R.color.tab_pressed_color));
            if (mHomePressed == null) {
                mHomePressed = getResources().getDrawable(R.drawable.nav_home_pressed);
            }
            drawable = mHomePressed;
        } else {
            mHomeTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (mHomeNormal == null) {
                mHomeNormal = getResources().getDrawable(R.drawable.nav_home_normal);
            }
            drawable = mHomeNormal;
        }
        if (null != drawable) {// 此处出现过NP问题，加保护
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mHomeTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void setMessageText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMessageTab.setTextColor(getResources().getColor(R.color.tab_pressed_color));
            if (mMessagePressed == null) {
                mMessagePressed = getResources().getDrawable(R.drawable.nav_message_pressed);
            }
            drawable = mMessagePressed;
        } else {
            mMessageTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (mMessageNormal == null) {
                mMessageNormal = getResources().getDrawable(R.drawable.nav_message_normal);
            }
            drawable = mMessageNormal;
        }
        if (null != drawable) {// 此处出现过NP问题，加保护
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mMessageTab.setCompoundDrawables(null, drawable, null, null);
        }
    }


    @Override
    public void setMineText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            mMineTab.setTextColor(getResources().getColor(R.color.tab_pressed_color));
            if (mMinePressed == null) {
                mMinePressed = getResources().getDrawable(R.drawable.nav_mine_pressed);
            }
            drawable = mMinePressed;
        } else {
            mMineTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (mMineNormal == null) {
                mMineNormal = getResources().getDrawable(R.drawable.nav_mine_normal);
            }
            drawable = mMineNormal;
        }
        if (null != drawable) {// 此处出现过NP问题，加保护
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            mMineTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        if (TAB_HOME.equals(tabId)) {
            setHomeText(true);
            setMessageText(false);
            setMineText(false);
        }else if (TAB_MESSAGE.equals(tabId)) {
            setHomeText(false);
            setMessageText(true);
            setMineText(false);
        }
/*        else if (TAB_SEEK.equals(tabId)) {
            setHomeText(false);
            setMessageText(false);
            setSeekText(true);
            setMineText(false);
        }*/
        else if (TAB_MINE.equals(tabId)) {
            setHomeText(false);
            setMessageText(false);
            setMineText(true);
        }
    }

    /**
     * 初始化相关监听
     */
    private void initListeners(){
        //初始化并添加会话变更监听
        initConversationServiceAndListener();
        //初始化联系人相关的监听
        initContactListeners();
        //添加联系人相关的监听
        addContactListeners();
        //初始化自定义会话
        initCustomConversation();
        //设置发送消息生命周期监听
        setMessageLifeCycleListener();
        //设置发送消息给黑名单中的联系人监听
        setSendMessageToContactInBlackListListener();
    }
    /**
     * 自定义会话示例展示系统通知的示例
     */
    private void initCustomConversation() {
        //CustomConversationHelper.addCustomConversation(SYSTEM_TRIBE_CONVERSATION, null);
        //CustomConversationHelper.addCustomConversation(SYSTEM_FRIEND_REQ_CONVERSATION, null);
        //CustomConversationHelper.addCustomViewConversation("myConversation","这个会话的展示布局可以自定义");
    }
    private void initConversationServiceAndListener() {
        mConversationUnreadChangeListener = new IYWConversationUnreadChangeListener() {

            //当未读数发生变化时会回调该方法，开发者可以在该方法中更新未读数
            @Override
            public void onUnreadChange() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mConversationService = LoginHelper.getInstance().getIMKit().getConversationService();
                        //获取当前登录用户的所有未读数
                        int unReadCount = mConversationService.getAllUnreadCount();
                        if (unReadCount > 0) {

                            mUnread.setVisibility(View.VISIBLE);
                            if (unReadCount < 100) {
                                mUnread.setText(unReadCount + "");
                            } else {
                                mUnread.setText("99+");
                            }
                        } else {
                            mUnread.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        };
    }

    private void addContactListeners(){
        if(mIMKit!=null){
            if(mContactOperateNotifyListener!=null)
                mIMKit.getContactService().addContactOperateNotifyListener(mContactOperateNotifyListener);
            if(mContactCacheUpdateListener!=null)
                mIMKit.getContactService().addContactCacheUpdateListener(mContactCacheUpdateListener);

        }
    }

    private void removeContactListeners(){
        if(mIMKit!=null){
            if(mContactOperateNotifyListener!=null)
                mIMKit.getContactService().removeContactOperateNotifyListener(mContactOperateNotifyListener);
            if(mContactCacheUpdateListener!=null)
                mIMKit.getContactService().removeContactCacheUpdateListener(mContactCacheUpdateListener);

        }
    }

    /**
     * 联系人相关操作通知回调，SDK使用方可以实现此接口来接收联系人操作通知的监听
     * 所有方法都在UI线程调用
     * SDK会自动处理这些事件，一般情况下，用户不需要监听这个事件
     * @author shuheng
     *
     */
    private void initContactListeners(){

        mContactOperateNotifyListener = new IYWContactOperateNotifyListener(){

            /**
             * 用户请求加你为好友
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             * @param message 附带的备注
             */
            @Override
            public void onVerifyAddRequest(IYWContact contact, String message) {
                YWLog.d(TAG, contact.getUserId()+"用户请求加你为好友");
                Notification.showToastMsg(mContext, contact.getUserId()+"用户请求加你为好友");

//                 //增加未读数的显示
//                 YWConversation conversation = mIMKit.getConversationService().getCustomConversationByConversationId(SYSTEM_FRIEND_REQ_CONVERSATION);
//                 if ( conversation!= null) {
//                     YWCustomConversationUpdateModel model = new YWCustomConversationUpdateModel();
//                     model.setIdentity(SYSTEM_FRIEND_REQ_CONVERSATION);
//                     model.setLastestTime(new Date().getTime());
//                     model.setUnreadCount(conversation.getUnreadCount() + 1);
//                     if(conversation.getConversationBody() instanceof YWCustomConversationBody){
//                         model.setExtraData(((YWCustomConversationBody)conversation.getConversationBody()).getExtraData());
//                     }
//                     if(mConversationService!=null)
//                     mConversationService.updateOrCreateCustomConversation(model);
//                 }

            }

            /**
             * 用户接受了你的好友请求
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             */
            @Override
            public void onAcceptVerifyRequest(IYWContact contact) {
                YWLog.d(TAG,contact.getUserId()+"用户接受了你的好友请求");
                Notification.showToastMsg(mContext,contact.getUserId()+"用户接受了你的好友请求");
            }
            /**
             * 用户拒绝了你的好友请求
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             * @param  contact 用户的信息
             */
            @Override
            public void onDenyVerifyRequest(IYWContact contact) {
                YWLog.d(TAG,contact.getUserId()+"用户拒绝了你的好友请求");
                Notification.showToastMsg(mContext,contact.getUserId()+"用户拒绝了你的好友请求");
            }

            /**
             * 云旺服务端（或其它终端）进行了好友添加操作
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             */
            @Override
            public void onSyncAddOKNotify(IYWContact contact) {
                YWLog.d(TAG,"云旺服务端（或其它终端）进行了好友添加操作对"+contact.getUserId());
                Notification.showToastMsg(mContext,"云旺服务端（或其它终端）进行了好友添加操作对"+contact.getUserId());

            }

            /**
             * 用户从好友名单删除了您
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             */
            @Override
            public void onDeleteOKNotify(IYWContact contact) {
                YWLog.d(TAG,contact.getUserId()+"用户从好友名单删除了您");
                Notification.showToastMsg(mContext,contact.getUserId()+"用户从好友名单删除了您");
            }
        };


        mContactCacheUpdateListener=new IYWContactCacheUpdateListener(){

            /**
             * 好友缓存发生变化(联系人备注修改、联系人新增和减少等)，可以刷新使用联系人缓存的UI
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param currentUserid                 当前登录账户
             * @param currentAppkey                 当前Appkey
             */
            @Override
            public void onFriendCacheUpdate(String currentUserid, String currentAppkey) {
                YWLog.d(TAG,"好友缓存发生变化");
                Notification.showToastMsg(mContext, "好友缓存发生变化");

            }

        };

    }

    private void setMessageLifeCycleListener(){
        mMessageLifeCycleListener = new IYWMessageLifeCycleListener() {
            /**
             * 发送消息前回调
             * @param conversation 当前消息所在会话
             * @param message      当前将要发送的消息
             * @return  需要发送的消息，若为null，则表示不发送消息
             */
            @Override
            public YWMessage onMessageLifeBeforeSend(YWConversation conversation, YWMessage message) {
                //todo 以下代码仅仅是示例，开发者无需按照以下方式设置，应该根据自己的需求对消息进行修改
                String cvsType = "单聊";
                if (conversation.getConversationType() == YWConversationType.Tribe){
                    cvsType = "群聊：";
                }
                String msgType = "文本消息";
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_IMAGE){
                    msgType = "图片消息";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_GEO){
                    msgType = "地理位置消息";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_AUDIO){
                    msgType = "语音消息";
                } else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS){
                    msgType = "自定义消息";
                }

                //设置APNS Push，如果开发者需要对APNS Push进行定制可以调用message.setPushInfo(YWPushInfo)方法进行设置，如果不需要APNS Push定制则不需要调用message.setPushInfo(YWPushInfo)方法
                YWPushInfo pushInfo = new YWPushInfo(1, cvsType + msgType, "dingdong", "我是自定义数据");
                message.setPushInfo(pushInfo);

                //根据消息类型对消息进行修改，切记这里只是示例，具体怎样对消息进行修改开发者可以根据自己的需求进行处理
                if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TEXT){
                    String content = message.getContent();
                    if (content.equals("55")) {
                        message.setContent("我修改了消息内容, 原始内容：55");
                        return message;
                    } else if (content.equals("66")){
                        YWMessage newMsg = YWMessageChannel.createTextMessage("我创建了一条新消息, 原始消息内容：66");
                        return newMsg;
                    } else if (content.equals("77")){
                        Notification.showToastMsg(mContext, "不发送该消息，消息内容为：77");
                        return null;
                    }
                }
                return message;
            }

            /**
             * 发送消息结束后回调
             * @param message   当前发送的消息
             * @param sendState 消息发送状态，具体状态请参考{@link com.alibaba.mobileim.conversation.YWMessageType.SendState}
             */
            @Override
            public void onMessageLifeFinishSend(YWMessage message, YWMessageType.SendState sendState) {
                Notification.showToastMsg(mContext, sendState.toString());
            }
        };
        mConversationService.setMessageLifeCycleListener(mMessageLifeCycleListener);
    }

    private void setSendMessageToContactInBlackListListener(){
        mSendMessageToContactInBlackListListener = new IYWSendMessageToContactInBlackListListener() {
            /**
             * 是否发送消息给黑名单中的联系人，当用户发送消息给黑名单中的联系人时我们会回调该接口
             * @param conversation 当前发送消息的会话
             * @param message      要发送的消息
             * @return true：发送  false：不发送
             */
            @Override
            public boolean sendMessageToContactInBlackList(YWConversation conversation, YWMessage message) {
                //TODO 开发者可用根据自己的需求决定是否要发送该消息，SDK默认不发送
                return true;
            }
        };
        mConversationService.setSendMessageToContactInBlackListListener(mSendMessageToContactInBlackListListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            //在Tab栏删除会话未读消息变化的全局监听器
            mConversationService.removeTotalUnreadChangeListener(mConversationUnreadChangeListener);
        }
        YWLog.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            LoginHelper.getInstance().initSDK_Sample(getBaseApplication());
            if (LoginHelper.getInstance().getIMKit()==null){
                return;
            }
            mConversationService = LoginHelper.getInstance().getIMKit().getConversationService();
            if (mConversationUnreadChangeListener!=null){
                //resume时需要检查全局未读消息数并做处理，因为离开此界面时删除了全局消息监听器
                mConversationUnreadChangeListener.onUnreadChange();

                //在Tab栏增加会话未读消息变化的全局监听器
                mConversationService.addTotalUnreadChangeListener(mConversationUnreadChangeListener);
            }

            Intent intent = getIntent();
            if (intent != null && intent.getStringExtra(LOGIN_SUCCESS) != null){
                tabhost.onTabChanged(TAB_HOME);
                getIntent().removeExtra(LOGIN_SUCCESS);
            }
            YWLog.i(TAG, "onResume");
        }

    }

    @Override
    protected void onDestroy() {
        YWLog.i(TAG, "onDestroy");
        super.onDestroy();

        if (mMessageNormal != null) {
            mMessageNormal.setCallback(null);
        }
        if (mMessagePressed != null) {
            mMessagePressed.setCallback(null);
        }

        if (mHomePressed != null) {
            mHomePressed.setCallback(null);
        }
        if (mHomeNormal != null) {
            mHomeNormal.setCallback(null);
        }

        if (mSeekPressed != null) {
            mSeekPressed.setCallback(null);
        }
        if (mSeekNormal != null) {
            mSeekNormal.setCallback(null);
        }

        if (mMinePressed != null) {
            mMinePressed.setCallback(null);
        }
        if (mMineNormal != null) {
            mMineNormal.setCallback(null);
        }
        //移除联系人相关的监听
        removeContactListeners();
    }


}
