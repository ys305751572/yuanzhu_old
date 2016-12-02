package com.bluemobi.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluemobi.pay.excute.PayRequest;

public class PayConfig {

	
	public final static String IP = "121.40.65.202";
	public final static String NOTIFY_URL_WEIXIN = "http://"+ IP +":8080/yuanzhu/api/weixiNotify";
	public final static String NOTIFY_URL_ALIPAY = "http://"+ IP +":8080/app/pay/alipayNotify";

//	public final static String NOTIFY_URL_WEIXIN = "http://112.74.197.62:8080/yuanzhu/api/weixiNotify";
//	public final static String NOTIFY_URL_ALIPAY = "http://112.74.197.62:8080/app/pay/alipayNotify";
	/**
	 * 
	 * @param request
	 * @param response
	 * @param sn
	 *            订单号
	 * @param totalFee
	 *            支付价格
	 * @param payWay
	 *            支付方式 WEIXIN,ALIPAY
	 * @return
	 */
	public static Map<String, Object> config(HttpServletRequest request, HttpServletResponse response, String sn,
			Double totalFee, String payWay) {
		Map<String, Object> params = new HashMap<String, Object>();
		if ("ALIPAY".equals(payWay)) {
			// 支付宝
			params.put("sn", sn);
			params.put("totelFee", totalFee);
			params.put("itemName", "支付");
			params.put("itemDesc", "支付");
			params.put("tag", "3");
			params.put("url", NOTIFY_URL_ALIPAY);
		} else {
			// 微信
			String prepayid = null; // 预支付款ID
			totalFee = totalFee*100;
			request.setAttribute("fee", String.valueOf(totalFee.intValue()));
			request.setAttribute("sn", sn);
			request.setAttribute("prepayid", prepayid);
			params = PayRequest.pay(request, response, NOTIFY_URL_WEIXIN);
		}
		return params;
	}
}
