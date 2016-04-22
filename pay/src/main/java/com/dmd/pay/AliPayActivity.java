package com.dmd.pay;

import com.dmd.pay.entity.PayInfo;
import com.dmd.pay.entity.PayResult;
import com.dmd.pay.utils.Alipay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class AliPayActivity extends FragmentActivity {

	private static final int SDK_PAY_FLAG = 1;

	private PayInfo payInfo;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(AliPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(AliPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(AliPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
				}
				payInfo=null;
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		Bundle extras = getIntent().getExtras();
		//获取绑定订单信息
		payInfo=(PayInfo)extras.getSerializable("payInfo");
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		if (!Alipay.checkKeyConfiguration()) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("支付宝调用失败，联系客服解决")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							finish();
						}
					}).show();
			return;
		}
		//支付宝调用
		new Alipay(mHandler,this).pay(payInfo);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		payInfo=null;
	}
}
