package com.dmd.zsb.api;

public class ApiConstants {

    public static final class Urls {

        public static final String API_BASE_URLS = "http://192.168.1.105:8080/TutorClient/v1.0/";
        public static final String API_IMG_BASE_URLS = "http://192.168.1.105:8080/TutorClient/";
        public static final String API_USER_INITDATA = "t_user_initdata.action";//注册
        public static final String API_USER_SIGNUP = "t_user_signup.action?";//注册
        public static final String API_USER_SIGNIN = "t_user_signin.action?";//登陆
        public static final String API_USER_SIGNOUT = "t_user_signout.action?";//退出
        public static final String API_USER_HOME = "t_user_home.action?";//首页
        public static final String API_USER_MINE = "t_user_mine.action?";//我的
        public static final String API_USER_DEMANDDETAIL = "t_user_demanddetail.action?";//需求详情

        public static final String API_USER_MYWALLET = "t_user_mywallet.action?";//我的钱包
        public static final String API_USER_MYORDER = "t_user_myorder.action?";//我的订单
        public static final String API_USER_MYEVALUATION = "t_user_myevaluation.action?";//我的评价
        public static final String API_USER_MYTRANSACTION = "t_user_mytransaction.action?";//我的交易请求

        public static final String API_USER_MYORDERDETAIL = "t_user_myorderdetail.action?";//我的订单详情
        public static final String API_USER_MYTRANSACTIONDETAIL = "t_user_mytransactiondetail.action?";//我的交易请求详情
        public static final String API_USER_MYEVALUATIONDETAIL = "t_user_myevaluationdetail.action?";//我的评价详情

        public static final String API_USER_BILLDETAIL = "t_user_billdetail.action?";//交易详情(钱包中的那个)

        public static final String API_USER_MYVOUCHERSDETAIL = "t_user_myvouchersdetail.action?";//我的代金券详情

        public static final String API_USER_CHANGESUBJECT = "t_user_changesubject.action?";//修改科目
        public static final String API_USER_CHANGEAVATAR = "t_user_changeavatar.action?";//修改头像
        public static final String API_USER_CHANGEPROFILE = "t_user_changeprofile.action?";//修改信息简介
        public static final String API_USER_CHANGEPASSWORD = "t_user_changepassword.action?";//修改密码
        public static final String API_USER_CHANGESIGNATURE= "t_user_changesignature.action?";//修改签名
        public static final String API_USER_CHANGENICKNAME = "t_user_changenickname.action?";//修改昵称

        public static final String API_USER_CERTIFY = "t_user_certify.action?";//认证
        public static final String API_USER_LOGFILE = "t_user_logFile.action?";//上传日志
        public static final String API_USER_FEEDBACK = "t_user_feedback.action?";//反馈

        public static final String API_USER_RECHARGE = "t_user_recharge.action?";//充值
        public static final String API_USER_WITHDRAWALS = "t_user_withdrawals.action?";//提现
        public static final String API_USER_BANKCARD = "t_user_bankcard.action?";//银行卡


        public static final String API_ORDER_GETORDERINFO = "t_order_getOrderInfo.action?";//获取订单信息
        public static final String API_ORDER_CANCEL = "t_order_cancel.action?";//取消订单
        public static final String API_ORDER_ACCEPT = "t_order_accept.action?";//接受订单
        public static final String API_ORDER_WORKDONE = "t_order_workdone.action?";//完成订单
        public static final String API_ORDER_GETRECEIVEDORDERS = "t_order_getReceivedOrders.action?";//接受订单列表
        public static final String API_ORDER_UPDATEORDERSTATUS = "t_order_updateOrderStatus.action?";//修改订单状态

    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
        public static final int PAGE_LIMIT = 20;
    }
}