package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Users;
import com.bluemobi.pro.service.impl.FeedbackServiceImpl;
import com.bluemobi.pro.service.impl.FiltrationServiceImpl;
import com.bluemobi.pro.service.impl.PlacardServiceImpl;
import com.bluemobi.pro.service.impl.UsersServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.utils.MD5;
import com.bluemobi.utils.SessionUtils;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class UsersController {

    @Autowired
    private UsersServiceImpl usersServiceImpl;

    @Autowired
    private FiltrationServiceImpl filtrationServiceImpl;

    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    @Autowired
    private PlacardServiceImpl placardServiceImpl;

    // ****************************框架外代码*********************************
    /**
     * 首页
     */
    @RequestMapping(value = { "/index", "/" })
    public String index(Model model) {
        Sysuser sysuser = SessionUtils.getCurrentUser();
        if (sysuser == null) {
            return "redirect:/login";
        }
        else {
        	model.addAttribute("sysuser", sysuser);
            return "main/main/main";
        }
    }

    /***
     * 获取首页列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/alluser")
    @ResponseBody
    public Page<List<Users>> alluser(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<Users>> page = usersServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/userlist")
    public String list() {
        return "main/users/userlist";
    }

    @RequestMapping("/userAdd")
    public String add() {
        return "main/users/userAdd";
    }

    @RequestMapping("/userUpdate")
    public String update(@RequestParam String id, HttpSession session) {
        try {
            Users users = usersServiceImpl.getUsersById(new Integer(id));
            session.setAttribute("user", users);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "main/users/userUpdate";
    }

    /***
     * 添加用户
     * 
     * @param map
     * @param model
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/addUser")
    @ResponseBody
    public String addUser(@RequestParam Map<String, String> map) {
        int flag = 0;
        try {
        	Map _map = usersServiceImpl.getUsersByLoginName(map.get("userName").toString());
        	if(_map == null){
        		map.put("password", MD5.md5((String) map.get("password")));
        		
        		int type = Integer.parseInt(map.get("userType").toString());
        		if(type != 4) {
        			map.put("provinceId", null);
        			map.put("cityId", null);
        			map.put("schoolId", null);
        		}
                flag = usersServiceImpl.adduser(map);
        	}else{
        		flag = -1;
        	}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flag + "";
    }

    /***
     * 修改用户
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = usersServiceImpl.updateuser(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /***
     * 修改用户
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/modifyPassword")
    @ResponseBody
    public String modifyPassword(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = usersServiceImpl.updatePassword(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /***
     * 删除用户
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/delete/user")
    @ResponseBody
    public String deleteUser(@RequestParam Map<String, Object> map) {
        Map<String, Object> flag = null;
        try {
            flag = usersServiceImpl.deleteuser(Integer.parseInt((String) map.get("id")));
            if (null != flag) {
                // 删除关键词表中该用户的所有数据
                filtrationServiceImpl.delete(map);
                // 删除反馈表中该用户的所有数据
                feedbackServiceImpl.deleteback(map);
                // 删除公告表中该用户的所有数据
                placardServiceImpl.delete(map);
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flag != null ? flag.get("data").toString() : null;
    }
}
