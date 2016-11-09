package com.bluemobi.pro.controller.webservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.service.impl.RankServiceImpl;
import com.bluemobi.sys.controller.BaseController;

@Controller
@RequestMapping(value = "/api/rank")
public class RankController extends BaseController{

	@Autowired
	private RankServiceImpl service;
	
	/**
	 * 
	 * @param params
	 * @return
	 * @Description: 获取群排行列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/grank" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getGrank(@RequestParam Map<String,Object> params) {
		List<?> grankList = null;
		try {
			grankList = service.getGroupRankList(params);
			this.doResp(grankList, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			this.doError();
			e.printStackTrace();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取安心排名
	 * @param params
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/crank" , method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String,Object> getCrank(@RequestParam Map<String,Object> params) {
		List<?> crankList = null;
		try {
			crankList = service.getSignRankList(params);
			this.doResp(crankList, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			this.doError();
			e.printStackTrace();
		}
		return this.getJsonString();
	}
}
