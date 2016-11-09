package com.bluemobi.pro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Grouptype;
import com.bluemobi.pro.pojo.Interest;
import com.bluemobi.pro.service.impl.InterestsServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class InterestsController {

    @Autowired
    private InterestsServiceImpl interestsServiceImpl;

    // ****************************框架外代码*********************************

    /***
     * 获取首页列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allinterest")
    @ResponseBody
    public Page<List<Interest>> allinterest(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<Interest>> page = interestsServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/interestlist")
    public String interestlist(HttpSession session) {
        Page<List<Interest>> page = interestsServiceImpl.list(null, 1, 10000);
        session.setAttribute("interestList", page.getRows());
        return "main/interests/interestlist";
    }

    /***
     * 新增或者更新
     * 
     * @param map
     * @param model
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/addOrupdateInterest")
    @ResponseBody
    public String addOrupdateInterest(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            if (null == map.get("id") || ((String) map.get("id")).equals("0")) {
            	Map _map = new HashMap();
            	_map.put("name",map.get("name"));
            	Interest rsMap = interestsServiceImpl.getById(_map);
            	if(rsMap == null){
            		offset = interestsServiceImpl.save(map);
            	}
            	else{
            		offset = -1;
            	}
            }
            else {
                offset = interestsServiceImpl.update(map);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /**
     * 处理--多选
     * 
     * @return
     */
    @RequestMapping("/deleteInterests")
    @ResponseBody
    public String deleteInterests(@RequestParam Map<String, Object> params) {
        // 要处理的数据集，以字符串的形式存在，中间用逗号隔开
        String ids = params.get("ary").toString();
        // 获取到要处理的id的数组
        String[] list = ids.split(",");
        // 调用更新方法，传入要处理id的数组
        return interestsServiceImpl.deletes(list);
    }
}
