package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Recharge  extends SugarRecord implements Serializable {

	public String rid;
	public String body;//商品描述
	public String discount;//折扣
	public String voucher;//代金券额度
	public String price;//商品单价
	public String out_trade_no;//商户订单号
	public String subject;//商品名称
	public String user;// 用户id

	public Recharge() {
	}

	public Recharge(String rid, String body, String discount, String voucher, String price, String out_trade_no, String subject, String user) {
		this.rid = rid;
		this.body = body;
		this.discount = discount;
		this.voucher = voucher;
		this.price = price;
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.user = user;
	}

	public void  fromJson(JSONObject jsonObject)  throws JSONException {
		if (null == jsonObject) {
			return;
		}

		JSONArray subItemArray;

		this.rid = jsonObject.optString("rid");
		this.body = jsonObject.optString("body");
		this.discount = jsonObject.optString("discount");
		this.voucher = jsonObject.optString("voucher");
		this.price = jsonObject.optString("price");
		this.out_trade_no = jsonObject.optString("out_trade_no");
		this.subject = jsonObject.optString("subject");
		this.user = jsonObject.optString("user");

		return ;
	}
	public JSONObject  toJson() throws JSONException{
		JSONObject localItemObject = new JSONObject();
		JSONArray itemJSONArray = new JSONArray();

		localItemObject.put("rid", rid);
		localItemObject.put("body", body);
		localItemObject.put("discount", discount);
		localItemObject.put("voucher", voucher);
		localItemObject.put("price", price);
		localItemObject.put("out_trade_no", out_trade_no);
		localItemObject.put("subject", subject);
		localItemObject.put("user", user);
		return localItemObject;
	}

}
