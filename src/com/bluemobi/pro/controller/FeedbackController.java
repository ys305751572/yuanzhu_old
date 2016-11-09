package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Feedback;
import com.bluemobi.pro.service.impl.FeedbackServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.utils.CookiesUtils;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackServiceImpl;

    // ****************************框架外代码*********************************

    /***
     * 获取首页列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allback")
    @ResponseBody
    public Page<List<Feedback>> allfeedback(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<Feedback>> page = feedbackServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/backlist")
    public String backlist() {
        return "main/feedback/feedbacklist";
    }

    @RequestMapping("/backShow")
    public String update(@RequestParam Map<String, Object> map, HttpSession session) {
        try {
            Map<String, Object> back = feedbackServiceImpl.getFeedbackById(map);
            session.setAttribute("back", back);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "main/feedback/feedbackshow";
    }

    /***
     * 处理反馈信息
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/updateBack")
    @ResponseBody
    public String updateBack(@RequestParam Map<String, Object> map, HttpSession session,
            HttpServletRequest request) {
        int offset = 0;
        try {
            map.put("backTime", System.currentTimeMillis());
            // 获取当前用户的编号
            Sysuser user = (Sysuser) session.getAttribute("loginUser");
            if (null == user) {
                Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
                map.put("stuUserId", new Integer((String) params.get("id")));
            }
            else {
                map.put("stuUserId", user.getId());
            }
            offset = feedbackServiceImpl.updateback(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /**
     * 处理反馈--多选
     * 
     * @return
     */
    @RequestMapping("/updateBacks")
    @ResponseBody
    public String updateBacks(@RequestParam Map<String, Object> params, HttpSession session,
            HttpServletRequest request) {
        // 要处理的数据集，以字符串的形式存在，中间用逗号隔开
        String ids = params.get("ary").toString();
        // 获取到要处理的反馈id的数组
        String[] list = ids.split(",");
        // 调用更新方法，传入要处理反馈id的数组
        return feedbackServiceImpl.updates(list, session, request);
    }
}
