package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.pojo.Years;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class StuuserController extends BaseController{

    @Autowired
    private StuUserServiceImpl stuUserServiceImpl;

    // ****************************框架外代码*********************************

    /***
     * 获取首页列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allstuuser")
    @ResponseBody
    public Page<List<StuUser>> allstuuser(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<StuUser>> page = stuUserServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/stuuserlist")
    public String stuuserlist(@RequestParam Map<String, Object> map, Model model) {
    	model.addAttribute("pages", map.get("pages"));
        return "main/users/stuuserlist";
    }

    @RequestMapping("/stuuserShow")
    public String stuuserShow(@RequestParam Map<String, Object> map, Model model) {
        try {
            Map<String, Object> stuuser = stuUserServiceImpl.getById(map);
            stuuser.put("pageNumber", map.get("pageNumber"));
            model.addAttribute("stuuser", stuuser);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "main/users/stuusershow";
    }

    
    /**
     * 叶松 2015-1-27
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/stuuserShow2")
    @ResponseBody
    public String getStuuserById(@RequestParam Map<String, Object> map, HttpSession session) {
    	int flag = -1;
    	String ids = map.get("ary") != null ? map.get("ary").toString(): "";
    	String[] idss = null;
    	if(StringUtils.isNotBlank(ids)){
			try {
				idss = ids.split(",");
				for (String id : idss) {
					if(StringUtils.isNotBlank(id)){
						Map<String, Object> stuuser = stuUserServiceImpl.getById2(Integer.parseInt(id));
						flag = checkUser(stuuser);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        return flag + "";
    }
    
    //yesong 2015-1-27
    public int checkUser(Map<String, Object> stuuser) {
    	int flag = -1;
		for (Map.Entry<String, Object> _user : stuuser.entrySet()) {
			String _key = _user.getKey();
			if("coverPic".equals(_key) || "contentPic".equals(_key)){
				if(_user.getValue() == null ||_user.getValue().toString().equals("")){
					flag = -1;
				}
			}
			
			if("recUserId".equals(_key)){
				if(_user.getValue() == null ||_user.getValue().toString().equals("") ){
					if(flag == -1)return flag;
				}else{
					flag = 1;
				}
			}
			
			else{
				if(_user.getValue() == null ||_user.getValue().toString().equals("") || "0".equals(_user.getValue().toString())){
					flag = -1;
					return flag;
				}
				else{
					flag = 1;
				}
			}
		}
		return flag;
	}

	/***
     * 处理信息
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/updatestuUser")
    @ResponseBody
    public String updatestuUser(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = stuUserServiceImpl.update(map);
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
    @RequestMapping("/updatestuUsers")
    @ResponseBody
    public String updatestuUsers(@RequestParam Map<String, Object> params) {
        // 调用更新方法，传入要处理反馈id的数组
        return stuUserServiceImpl.updates(params);
    }

    /**
     * 根据条件查询群规模
     * 
     * @return
     */
    @RequestMapping("findYearsByParams")
    @ResponseBody
    public List<Years> findScaleByParams(@RequestParam Map<String, Object> params) {
        List<Years> li = null;
        try {
            li = stuUserServiceImpl.findYearsByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Years year = new Years();
        year.setId(-1);
        year.setYear("所有入学年份");
        li.add(0, year);
        return li;
    }
    
    /**
	 * 修改任务审核状态
	 * @param params
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/exchangeVliadte", method = RequestMethod.POST)
	@ResponseBody
	public String exchangeTask(@RequestParam Map<String,Object> params) {
		int flag = -1;
		try {
			flag = stuUserServiceImpl.exchangeVlidate(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "" + flag;
	}
}
