package com.dmd.zsb.utils;


import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.api.ApiConstants;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UriHelper {

    private static volatile UriHelper instance = null;

    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;


    private UriHelper() {
    }

    public static UriHelper getInstance() {
        if (null == instance) {
            synchronized (UriHelper.class) {
                if (null == instance) {
                    instance = new UriHelper();
                }
            }
        }
        return instance;
    }
    //页码处理
    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % PAGE_LIMIT != 0 ? (totalNumber / PAGE_LIMIT + 1) : totalNumber / PAGE_LIMIT;
        } else {
            return 0;
        }
    }

    private String urlToString(boolean flag,String action,JsonObject json){
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(ApiConstants.Urls.API_BASE_URLS);
        stringbuffer.append(action);
        if (flag){
            stringbuffer.append("json=");
            stringbuffer.append(json);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SSS");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        TLog.d("UriHelper",time+" 请求 ："+stringbuffer.toString().trim());
        return stringbuffer.toString().trim();
    }

    //初始化数据
    public String InitData(JsonObject json){
        return urlToString(false,ApiConstants.Urls.API_USER_INITDATA,json);
    }

    //注册
    public String signUp(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNUP,json);
    }
    //登陆
    public String signIn(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNIN,json);
    }
    //退出
    public String signOut(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNOUT,json);
    }

    //主页
    public String home(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_HOME,json);
    }
    //需求详情
    public String demandDetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_DEMANDDETAIL,json);
    }
    //我的
    public String mine(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MINE,json);
    }

    //修改用户简介
    public String changeprofile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPROFILE,json);
    }
    //修改头像
    public String changeAvatar(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEAVATAR,json);
    }
    //修改密码
    public String changePassword(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPASSWORD,json);
    }
    //修改签名
    public String changesignature(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGESIGNATURE,json);
    }
    //修改昵称
    public String changenickname(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGENICKNAME,json);
    }




    //我的钱包
    public String mywallet(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYWALLET,json);
    }
    //我的订单
    public String myorder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYORDER,json);
    }
    //我的评价
    public String myevaluation(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYEVALUATION,json);
    }
    //我的交易请求
    public String mytransaction(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYTRANSACTION,json);
    }



    //我的交易请求详情
    public String mytransactiondetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYTRANSACTIONDETAIL,json);
    }
    //我的订单详情
    public String myorderdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYORDERDETAIL,json);
    }
    //我的评价详情
    public String myevaluationdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYEVALUATIONDETAIL,json);
    }
    //交易详情(钱包中的那个)
    public String billdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BILLDETAIL,json);
    }
    //我的代金券详情
    public String myvouchersdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYVOUCHERSDETAIL,json);
    }

    //充值
    public String recharge(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_RECHARGE,json);
    }
    //提现
    public String withdrawals(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_WITHDRAWALS,json);
    }
    //银行卡
    public String bankcard(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BANKCARD,json);
    }



    //上传日志
    public String logFile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_LOGFILE,json);
    }
    //反馈
    public String seedFeedback(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_FEEDBACK,json);
    }
    //认证
    public String certify(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CERTIFY,json);
    }


    //订单信息
    public String getOrderInfo(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_GETORDERINFO,json);
    }
    //取消订单
    public String cancelOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CANCEL,json);
    }
    //接受订单
    public String acceptOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_ACCEPT,json);
    }
    //完成订单
    public String workdone(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_WORKDONE,json);
    }

    //接受订单列表
    public String getReceivedOrders(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_GETRECEIVEDORDERS,json);
    }
    //修改订单状态
    public String updateOrderStatus(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_UPDATEORDERSTATUS,json);
    }

}
