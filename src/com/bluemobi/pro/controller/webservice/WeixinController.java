package com.bluemobi.pro.controller.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pay.util.XMLUtil;
import com.bluemobi.pro.service.impl.WeixinServiceImpl;
import com.bluemobi.utils.Result;

@Controller
@RequestMapping(value = "/api")
public class WeixinController {

	@Autowired
	private WeixinServiceImpl weixinServiceImpl;

	/**
	 * 获取预支款ID
	 * @param userId
	 * @param coin
	 * @return
	 */
	@RequestMapping(value = "/prepayid", method = RequestMethod.POST)
	@ResponseBody
	public Result getPrepayId(HttpServletRequest request, HttpServletResponse response, Integer userId, Integer coin) {
		
		Map<String, Object> resultMap = null;
		try {
			resultMap = weixinServiceImpl.getPrepayId(request, response, userId, coin);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success(resultMap,"weixin");
	}
	
	@RequestMapping(value = "/weixiNotify")
	public void weixiNotify(HttpServletRequest request) {
		Map<String, Object> result = parse(request);
		String status = result.get("result_code").toString();
		if ("SUCCESS".equals(status)) {
			System.out.println("weixin pay recall success !!");
			String sn = result.get("out_trade_no").toString();
			double amount = Double.parseDouble(result.get("total_fee").toString());

			try {
				weixinServiceImpl.callSuccess(sn, amount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> parse(HttpServletRequest request) {
		Map<String, Object> resultMap = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			String result = "";
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			System.out.println("result:" + result);
			resultMap = XMLUtil.doXMLParse(result);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
