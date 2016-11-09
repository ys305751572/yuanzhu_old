package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Activities;
import com.bluemobi.pro.pojo.StuUser;
import com.bluemobi.pro.service.impl.ActivitiesServiceImpl;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class ActivitiessController {

    @Autowired
    private ActivitiesServiceImpl activitiesServiceImpl;
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
    @RequestMapping("/allactivities")
    @ResponseBody
    public Page<List<Activities>> allactivities(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<Activities>> page = activitiesServiceImpl.list(map, pages, rows);
        return page;
    }

    /***
     * 获取参加活动的用户列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/alljoinperson")
    @ResponseBody
    public Page<List<StuUser>> alljoinperson(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<StuUser>> page = activitiesServiceImpl.personlist(map, pages, rows);
        return page;
    }

    @RequestMapping("/activitieslist")
    public String activitieslist() {
        return "main/activities/activitieslist";
    }

    @RequestMapping("/joinpersonlist")
    public String joinpersonlist(@RequestParam String id, HttpSession session) {
        session.setAttribute("activitId", id);
        return "main/activities/joinpersonlist";
    }

    @RequestMapping("/activitiesShow")
    public String activities(@RequestParam Map<String, Object> map, HttpSession session) {
        try {
            Map<String, Object> activities = activitiesServiceImpl.findOneById(map);
            session.setAttribute("activities", activities);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "main/activities/activitiesshow";
    }

    @RequestMapping("/stuuserShow1")
    public String stuuserShow(@RequestParam Map<String, Object> map, HttpSession session) {
        try {
            Map<String, Object> stuuser = stuUserServiceImpl.getById(map);
            session.setAttribute("stuuser", stuuser);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "main/users/stuusershow1";
    }
    
    /***
     * 处理信息
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/updateActivities")
    @ResponseBody
    public String updateActivities(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = activitiesServiceImpl.update(map);
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
    @RequestMapping("/updateActivitiess")
    @ResponseBody
    public String updateActivitiess(@RequestParam Map<String, Object> params) {
        // 调用更新方法，传入要处理反馈id的数组
        return activitiesServiceImpl.updates(params);
    }
}
