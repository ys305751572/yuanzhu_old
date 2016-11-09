package com.bluemobi.pro.controller.webservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.service.impl.TCordServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;

/**
 * 
 * <p>Title: TCcodeController.java</p> 
 * <p>Description: 省份，入学年份，城市等controller</p> 
 * @author yesong 
 * @date 2014-11-17 上午11:00:47
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping(value = "/api")
public class TCcodeController extends BaseController{
	
	@Autowired
	private TCordServiceImpl tCordService;
	
	/**
	 * 获取省份
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/common/getProvinceList" , method=RequestMethod.POST)
	@ResponseBody
	public Map getProvinceList(@RequestParam Map<String, Object> map ){
		try {
			List list = tCordService.getProvince();
			this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取城市
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/common/getCityList" , method=RequestMethod.POST)
	@ResponseBody
	public Map getCity(@RequestParam Map<String, Object> map ) {
		try {
			int provinceId = Integer.parseInt(map.get("provinceId").toString());
			
			if(provinceId == 1 || provinceId ==2 || provinceId == 3 || provinceId == 4){
				map.put("cityId", provinceId);
			}
			List list = tCordService.getCity(map); 
			this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取学校学院专业列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/person/school" ,method=RequestMethod.POST)
	@ResponseBody
	public Map getScs(@RequestParam Map<String, Object> map){
		try {
			Page page = tCordService.getScs(map);
			this.initPage(map, page);
			this.doResp(page.getRows(),"list", this.page, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取入学年份
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/person/year" ,method=RequestMethod.POST)
	@ResponseBody
	public Map getYears(@RequestParam Map<String, Object> map){
		try {
			List list = tCordService.getYear();
			this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取兴趣
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/person/interest" ,method=RequestMethod.POST)
	@ResponseBody
	public Map getInterest(@RequestParam Map<String, Object> map){
		try {
			List list = tCordService.getInterest();
			this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
	
	/**
	 * 获取标签
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/person/label" ,method=RequestMethod.POST)
	@ResponseBody
	public Map getLabel(@RequestParam Map<String, Object> map){
		try {
			List list = tCordService.getLabel();
			this.doResp(list, "list", null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
}
