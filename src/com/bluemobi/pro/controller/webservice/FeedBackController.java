package com.bluemobi.pro.controller.webservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.service.impl.FeedBackServceImpl;
import com.bluemobi.sys.controller.BaseController;

/**
 * 
 * <p>Title: FeedBackController.java</p> 
 * <p>Description: 反馈controller</p> 
 * @author yesong 
 * @date 2014年12月15日 上午10:59:09
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Controller
@RequestMapping(value = "/api")
public class FeedBackController extends BaseController{
	
	@Autowired
	private FeedBackServceImpl service;
	
	/**
	 * 新增反馈
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/feedback/create" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> insertFeedBack(@RequestParam Map<String, Object> params) {
		int offest = 0; 
		try {
			offest = service.saveFeedBack(params);
			if(offest > 0){
				this.doResp(DATA_NULL, null, null, STATUS_SUCCESS, MSG_NULL);
			}
			else{
				this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_08);
			}
		} catch (Exception e) {
			e.printStackTrace();
			doError();
		}
		return getJsonString();
	}
}
