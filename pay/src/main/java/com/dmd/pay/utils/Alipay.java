package com.dmd.pay.utils;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.dmd.pay.entity.PayInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Alipay {

    // 商户PID
    public static final String PARTNER = "2088121368741032";
    // 商户收款账号
    public static final String SELLER = "656923138@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANjOHdsVgS+QGyIf" +
            "h0SLjazkCqq6nqzkIUtaogMwq8uWRXnpa0jepQ4fm2mkOq1Dxy9gx3P0Y0uAMXTS" +
            "vkC0TY9qby4yMaPQPA33OIn9Cv/XoMLQWiLRyKijmvQpy6r1riT2ShE3BWVjWgy8" +
            "2quiB7B4W9pjNRGe3qHoaCzLY0rxAgMBAAECgYBiDm8wbHx+T5YGC+55EQkl3gn5" +
            "B+jGV3O7iiVmffflX3TS28CbN5+9UIxQ7mdfgB0xj3sGNc1HE5bKuJo115aP5qZ5" +
            "WpxEy+I1iblZwT36tZ1dLdhDCtt3LjKbBj33enH7r+9HoZq0OHD6az92hKUlcAhE" +
            "UnqIpBux5qP/QJe2lQJBAPLK7eDL5lVZm2ZzJj5QKyH++yGe8iEJVjr9qY+JtUMw" +
            "sBln1T0eW5JsjvNmX1MO12RxhboRJNT/Nzl0sVhtd0MCQQDkmUqPclZqySWjngT8" +
            "oZhMpM38SyFTIUL4Gy2GL6KXazBSovc2Pn60KnNGLXzn+4QXXStubexMo0vzgFj1" +
            "Ks+7AkAMcag1kF3S3A/h9V7tbLB5QpdehsYEOXVI+r1ZFQbMVc+AzdiZFFg/Lf85" +
            "rHKQUD9r74GaIszPefE7qaZ5UMr/AkAkcnBZ2KAAnJt8r8gZw2X16lzROeVRTL9R" +
            "sXubpmKlA7kLo+IIawRmk1IXwU7l6csQspEnJS2EtpB4fWExjabbAkEAp6aHMfgv" +
            "zSWv26CdKz/x/ZO9wusKnAn86ZLQPCeMcN/pYITkZZ1mK48R+NVlccC7M3taFXM+" +
            "N+Qb6R91AV+GvA==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkr" +
            "IvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1" +
            "efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    public static final int SDK_PAY_FLAG = 1;

    private Handler mHandler;
    private Activity activity;
    private String orderNo;

    public Alipay(Handler handler, Activity activity) {
        mHandler = handler;
        this.activity = activity;
    }

    public static boolean checkKeyConfiguration(){
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            return false;
        }else {
            return true;
        }
    }
    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(PayInfo payinfo) {
        // 订单
        DecimalFormat df = new DecimalFormat("0.00");
        String orderInfo = getOrderInfo(payinfo.getName(), payinfo.getDesc()+ " ", df.format(payinfo.getPrice() * payinfo.getRate()));
        Log.e("Alipay",orderInfo);
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        Log.e("Alipay",payInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://www.cqdmd.com/v1.0/t_order_android_alipay.action"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);

        this.orderNo = key;
        return key;
    }

    /**
     * 获取已经生产的订单编号
     *
     * @return
     */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

}