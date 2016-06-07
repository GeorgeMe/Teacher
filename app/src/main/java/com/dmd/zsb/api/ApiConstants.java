package com.dmd.zsb.api;

public class ApiConstants {

    public static final class Urls {

        public static final String API_DEVELOPMENT_URLS = "http://192.168.1.55:8080/TutorClient/v1.0/";
        public static final String API_BASE_URLS = "http://www.cqdmd.com/v1.0/";

        public static final String API_USER_INITDATA = "t_user_initdata.action?";//初始化
        public static final String API_USER_SIGNUP = "t_user_signup.action?";//注册
        public static final String API_USER_SIGNIN = "t_user_signin.action?";//登陆
        public static final String API_USER_SIGNOUT = "t_user_signout.action?";//退出

        public static final String API_USER_HOME = "t_user_home.action?";//主页
        public static final String API_USER_MINE = "t_user_mine.action?";//我的
        public static final String API_USER_USERDETAIL = "t_user_userdetail.action?";//老师详情

        public static final String API_USER_MYWALLET= "t_user_mywallet.action?";//我的钱包
        public static final String API_USER_MYORDER = "t_user_myorder.action?";//我的订单
        public static final String API_USER_MYEVALUATION = "t_user_myevaluation.action?";//我的评价
        public static final String API_USER_MYDEMAND = "t_user_mydemand.action?";//我的需求
        public static final String API_USER_MYVOUCHERS = "t_user_myvouchers.action?";//我的代金券
        public static final String API_USER_BILLDETAIL = "t_user_billdetail.action?";//交易记录（钱包里面）

        public static final String API_USER_MYORDERDETAIL = "t_user_myorderdetail.action?";//我的订单详情
        public static final String API_USER_MYEVALUATIONDETAIL = "t_user_myevaluationdetail.action?";//我的评价详情
        public static final String API_USER_MYDEMANDDETAIL = "t_user_mydemanddetail.action?";//我的需求详情
        public static final String API_USER_MYVOUCHERSDETAIL = "t_user_myvouchersdetail.action?";//我的代金券详情


        public static final String API_USER_RECHARGE = "t_user_recharge.action?";//充值
        public static final String API_USER_WITHDRAWALS = "t_user_withdrawals.action?";//提现
        public static final String API_USER_BANKCARD = "t_user_bankcard.action?";//银行卡
        public static final String API_USER_VOUCHERS = "t_user_vouchers.action?";//代金券

        public static final String API_USER_CHANGEAVATAR = "t_user_changeavatar.action?";//修改头像
        public static final String API_USER_CHANGEBRIEF = "t_user_changebrief.action?";//修改信息简介
        public static final String API_USER_CHANGEPASSWORD = "t_user_changepassword.action?";//修改密码
        public static final String API_USER_CHANGESIGNATURE= "t_user_changesignature.action?";//修改签名
        public static final String API_USER_CHANGENICKNAME = "t_user_changenickname.action?";//修改昵称
        public static final String API_USER_FEEDBACK = "p_user_feedback.action?";//反馈

        public static final String API_USER_CERTIFY = "t_user_certify.action?";//认证
        public static final String API_USER_LOGFILE = "t_user_logFile.action?";//上传日志

        public static final String API_ORDER_CANCEL = "t_order_cancel.action?";//取消订单
        public static final String API_ORDER_ACCEPT = "t_order_accept.action?";//接受订单
        public static final String API_ORDER_WORKDONE = "t_order_workdone.action?";//完成订单
        public static final String API_ORDER_CONFIRMPAY = "p_order_confirmpay.action?";//确认支付

    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
        public static final int PAGE_LIMIT = 20;
    }
}