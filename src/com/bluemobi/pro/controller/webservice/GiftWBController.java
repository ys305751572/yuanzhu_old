package com.bluemobi.pro.controller.webservice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.service.impl.GiftServiceImpl;
import com.bluemobi.sys.controller.BaseController;


@Controller
@RequestMapping(value="/api/gift")
public class GiftWBController extends BaseController{

	@Autowired
	private GiftServiceImpl service;
	
	/**
	 * 获取礼品列表
	 * @param params
	 * @return
	 * @Description:
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getGiftPage(@RequestParam Map<String,Object> params) {
		List<Map<String, Object>> list = null;
		try {
			list = service.getGiftList(params);
			this.doResp(list, "gift_list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			this.doError();
			e.printStackTrace();
		}
		return this.getJsonString();
	} 
	
	/**
	 * 详情
	 * @param params
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/detail" , method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> getGiftDetail(@RequestParam Map<String,Object> params) {
		try {
			Map reParmas = service.getGiftDetail(params) ;
			Map<String,Object> map = (reParmas == null ? Collections.EMPTY_MAP : reParmas);
			this.doResp(map, "gift_detail", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			this.doError();
			e.printStackTrace();
		}
		return this.getJsonString();
	}
	
	/**
	 * 兑换
	 * @param params
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/exchange" , method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String,Object> exchangeGift(@RequestParam Map<String,Object> params) {
		try {
			int flag = service.exchangeGift(params);
			if(flag == -1) {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_FAIL, Constant.ERROR_12);
			}
			else {
				this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
			}
		} catch (Exception e) {
			this.doError();
			e.printStackTrace();
		}
		return this.getJsonString();
	}
}
