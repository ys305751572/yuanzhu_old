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
import com.bluemobi.pro.service.impl.GrouptypeServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class GrouptypeController {

    @Autowired
    private GrouptypeServiceImpl grouptypeServiceImpl;

    // ****************************框架外代码*********************************

    @RequestMapping("/grouptypelist")
    public String grouptypelist(HttpSession session) {
        Page<List<Grouptype>> page = grouptypeServiceImpl.list(null, 1, 10000);
        session.setAttribute("grouptypeList", page.getRows());
        return "main/grouptype/grouptypelist";
    }

    /***
     * 添加或更新
     * 
     * @param map
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/addOrupdateGroupType")
    @ResponseBody
    public String addOrupdateGroupType(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            if (null == map.get("id") || ((String) map.get("id")).equals("0")) {
            	Map _map = new HashMap();
            	_map.put("name",map.get("name"));
            	Grouptype rsMap = grouptypeServiceImpl.getById(_map);
            	if(rsMap == null){
            		offset = grouptypeServiceImpl.save(map);
            	}
            	else{
            		offset = -1;
            	}
            }
            else {
                offset = grouptypeServiceImpl.update(map);
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
    @RequestMapping("/deleteGroupTypes")
    @ResponseBody
    public String deleteGrouptypes(@RequestParam Map<String, Object> params) {
        // 要处理的数据集，以字符串的形式存在，中间用逗号隔开
        String ids = params.get("ary").toString();
        // 获取到要处理的id的数组
        String[] list = ids.split(",");
        // 调用更新方法，传入要处理id的数组
        return grouptypeServiceImpl.deletes(list);
    }
}
