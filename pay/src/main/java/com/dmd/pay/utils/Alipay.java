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

/**
 * Created by Administrator on 2016/4/12.
 */
public class Alipay {

    // 商户PID
    public static final String PARTNER = "2088121368741032";
    // 商户收款账号
    public static final String SELLER = "656923138@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKniUeAPz8RcnwgA" +
            "rEXkVmAcRUfXn4blR5XISPJUobgnPAwjRJIflaoA+jAsKMgHwlHuWpiHq17hvOh6" +
            "Y5yzGBu30ADxcsZZFkUVAdE6kXHIzZRbFbfzUDzdPBNoYwIgIFjZo38IT5uLsuIa" +
            "5SrXy6xf6tzu3119VFOkG8ZkFQJzAgMBAAECgYEAppY4pVe77BjLFaTbSboL/Tkb" +
            "ssQ9OaFRng4FgiP9ZUCMAHcBAa0ZLNjCfUSARQ5evcrWaeX35RXFEc8aKQnx43tP" +
            "WsHutZX9zAVzHJMqOvby/7cJFya/SUwb5bwRD8GpjPnIk420WhAMVmKSohN3BRU+" +
            "WbL/PdndwM0FDVGLDwkCQQDQ5nBdJhF2op6C+fNqp4nArSKtJB4ci8RLs4x+y2sM" +
            "ioMC3RCY7k+7y/e4KQT3ilmQiogyDpvol3ukO4CnYam9AkEA0C/muLHv9g6a4cCJ" +
            "psrxK1As/ufBxzjaV5GL2SIT0jLk+Za5PHmn/SUj41CBzpR5FiZGjxK2MRV5XkYW" +
            "55bn7wJBAJ8vOmiA3cXdXZTJCyg2ys28ITR21UtDZi2ZgHIOaGCCHBtOgZtH3hJo" +
            "9zeqalKHZoCyT951K3EuHdWkUgE6drECQEm4XKQUZlukSlYcnuHQRVxIFNbi7GyG" +
            "hlKx+GqYNZqeaRS0ub1fewwaJb4t2Pl0+/fK7/tj7d4ts3+dpCi3+EMCQQCIz6uq" +
            "/EXRtNALhe9bcAXG9Mc0dkoabO+y625unEG/7V6L6JZ8SD2zxVVS+h/v7u80BlTu" +
            "cr5FzLk1Ixnq9yYU";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp4lHgD8/EXJ8IAKxF5FZgHEVH" +
            "15+G5UeVyEjyVKG4JzwMI0SSH5WqAPowLCjIB8JR7lqYh6te4bzoemOcsxgbt9AA" +
            "8XLGWRZFFQHROpFxyM2UWxW381A83TwTaGMCICBY2aN/CE+bi7LiGuUq18usX+rc" +
            "7t9dfVRTpBvGZBUCcwIDAQAB";

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
        this.orderNo=payinfo.getOrder_sn();
        String orderInfo = getOrderInfo(payinfo.getOrder_sn(),payinfo.getName(), payinfo.getDesc()+ " ", df.format(payinfo.getPrice() * payinfo.getRate()));
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
    public String getOrderInfo(String out_trade_no,String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

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
/*
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
*/

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