package com.bluemobi.pro.controller.webservice;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bluemobi.pro.pojo.Placard;
import com.bluemobi.pro.pojo.Province;
import com.bluemobi.pro.pojo.Scs;
import com.bluemobi.pro.service.impl.PlacardServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.Result;

/**
 * 
 * <p>Title: PlacardController.java</p> 
 * <p>Description: 公告controller</p> 
 * @author yesong 
 * @date 2014-11-17 上午08:52:01
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Controller
@RequestMapping(value = "/api")
public class PlacardWBController extends BaseController{

    @Autowired
    private PlacardServiceImpl placardServiceImpl;
    
    /**
     * 查询公告列表
     * @param map
     * @return
     */
    @RequestMapping(value="/placard/list",method=RequestMethod.POST)
    @ResponseBody
    public Map queryList(@RequestParam Map<String,Object> map) {
    	String pageNum =  (map.get("pageNum") == null ? "1" : (String) map.get("pageNum"));
    	String pageSize = (map.get("pageSize") == null ? "10" : (String) map.get("pageSize"));
    	try {
    		Page page = placardServiceImpl.list1(map, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        	
    		this.initPage(map, page);
    		this.doResp(page.getRows(), "list", this.page, STATUS_SUCCESS, MSG_NULL);
    	} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
    	return this.getJsonString();
    }
    
    /**
     * 查询公告详情
     * @param map
     * @return
     */
    @RequestMapping(value="/placard/info",method = RequestMethod.POST)
    @ResponseBody
    public Map queryDetail(@RequestParam Map<String, Object> map ) {
    	Map placard = null;
    	try{
    		String placardId = (String) map.get("placardId");
    		placard = placardServiceImpl.findOneById(placardId);
    		this.doResp(placard, "placard", null, STATUS_SUCCESS, MSG_NULL);
    	}catch (Exception e) {
    		e.printStackTrace();
    		this.doError();
    	}
    	return this.getJsonString();
    }
    
    /**
     * 新增咨询阅读记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/placard/record/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertPlacardRecord(@RequestParam Map<String,Object> map ) {
    
    	try {
			placardServiceImpl.insertPlacardRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure();
		}
    	return Result.success();
    }
}
