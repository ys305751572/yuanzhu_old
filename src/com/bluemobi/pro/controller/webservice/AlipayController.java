package com.bluemobi.pro.controller.webservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bluemobi.pro.service.impl.AlipayServiceImpl;
import com.bluemobi.sys.controller.BaseController;

/**
 * 支付宝支付controller
 * @author sye
 *
 */
@Controller
@RequestMapping(value = "/api")
public class AlipayController extends BaseController{
	
	@Autowired
	private AlipayServiceImpl service;
	
	/**
	 * 获取商品号，商品名，需要支付的价格(人民币)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/alipay/getUUIDAndName",method=RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> getUUIDAndName(@RequestParam Map<String, Object> params) {
		try {
			Map map = service.getUUIDAndName(params);
			if(map != null) {
				this.doResp(map, "data", null, STATUS_SUCCESS, MSG_NULL);
			}
			else{
				this.doError();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	@Deprecated
	@RequestMapping(value="/alipay/callMeStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<?,?> callMeStatus(@RequestParam Map<String, Object> params) throws Exception{
		String orderId = (String) params.get("orderID");
		String orderName = (String) params.get("orderName");
		service.deleteCoinLog(orderId, orderName);
		return null;
	}
	
	/**
	 * 根据充值返回的状态更新表，以及记录日志
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/alipay/respResult",method=RequestMethod.POST)
	public void getStatusAndResp(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		System.out.println("Alipay callback succes!!");
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		try {
			service.getStatusAndResp(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
