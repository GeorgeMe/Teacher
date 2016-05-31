package com.dmd.zsb.utils;


import android.util.Log;

import com.dmd.zsb.api.ApiConstants;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UriHelper {

    private static volatile UriHelper instance = null;
    //true开发者模式  false 正式环境
    private static boolean development=false;
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

    private String urlToString(boolean flag,String action,JSONObject json){
        StringBuffer stringbuffer = new StringBuffer();
        if (development){
            stringbuffer.append(ApiConstants.Urls.API_DEVELOPMENT_URLS);
        }else {
            stringbuffer.append(ApiConstants.Urls.API_BASE_URLS);
        }
        stringbuffer.append(action);
        if (flag){
            stringbuffer.append("json=");
            stringbuffer.append(json);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SSS");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        if (development){
            Log.e("UriHelper",time+" 调试请求 ："+stringbuffer.toString().trim());
        }else {
            Log.e("UriHelper",time+" 正式请求 ："+stringbuffer.toString().trim());
        }

        return stringbuffer.toString().trim();
    }

    //初始化数据
    public String InitData(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_INITDATA,json);
    }
    //注册
    public String signUp(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNUP,json);
    }
    //登陆
    public String signIn(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNIN,json);
    }
    //退出
    public String signOut(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNOUT,json);
    }


    //首页
    public String home(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_HOME,json);
    }
    //找老师
    public String findteacher(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_FINDTEACHER,json);
    }
    //我的
    public String mine(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MINE,json);
    }
    //用户详情
    public String userDetail(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_USERDETAIL,json);
    }


    //我的钱包
    public String mywallet(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYWALLET,json);
    }
    //我的订单
    public String myorder(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYORDER,json);
    }
    //我的评价
    public String myevaluation(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYEVALUATION,json);
    }
    //我的需求
    public String mydemand(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYDEMAND,json);
    }
    //我的代金券
    public String myvouchers(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYVOUCHERS,json);
    }
    //交易记录（钱包里面）
    public String billdetail(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BILLDETAIL,json);
    }


    //充值
    public String recharge(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_RECHARGE,json);
    }
    //提现
    public String withdrawals(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_WITHDRAWALS,json);
    }
    //银行卡
    public String bankcard(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BANKCARD,json);
    }
    //代金券
    public String vouchers(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_VOUCHERS,json);
    }


    //我的订单详情
    public String myorderdetail(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYORDERDETAIL,json);
    }
    //我的评价详情
    public String myevaluationdetail(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYEVALUATIONDETAIL,json);
    }
    //我的需求详情
    public String mydemanddetail(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYDEMANDDETAIL,json);
    }
    //我的代金券详情
    public String myvouchersdetail(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYVOUCHERSDETAIL,json);
    }

    //修改用户简介
    public String changeBrief(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEBRIEF,json);
    }
    //修改头像
    public String changeAvatar(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEAVATAR,json);
    }
    //修改密码
    public String changePassword(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPASSWORD,json);
    }
    //修改签名
    public String changesignature(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGESIGNATURE,json);
    }
    //修改昵称
    public String changenickname(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGENICKNAME,json);
    }

    //上传日志
    public String logFile(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_LOGFILE,json);
    }
    //反馈
    public String seedFeedback(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_FEEDBACK,json);
    }
    //认证
    public String certify(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CERTIFY,json);
    }


    //保存订单信息
    public String saveOrder(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_SAVEORDER,json);
    }
    //取消订单
    public String cancelOrder(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CANCEL,json);
    }
    //支付订单
    public String payOrder(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_PAY,json);
    }
    //确认支付
    public String confirmpay(JSONObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CONFIRMPAY,json);
    }

}
