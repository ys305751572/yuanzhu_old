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

import com.bluemobi.pro.pojo.Filtration;
import com.bluemobi.pro.service.impl.FiltrationServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.utils.CookiesUtils;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class FiltrationController {

    @Autowired
    private FiltrationServiceImpl filtrationServiceImpl;

    /***
     * 获取列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allfiltration")
    @ResponseBody
    public Page<List<Filtration>> allfiltration(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<Filtration>> page = filtrationServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/filtrationlist")
    public String list() {
        return "main/filtration/filtrationlist";
    }

    /***
     * 添加或更新
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/addOrupdateFiltration")
    @ResponseBody
    public String addOrupdateFiltration(@RequestParam Map<String, Object> map,
            HttpServletRequest request, HttpSession session) {
        int flag = 0;
        // 获取当前用户的编号
        Sysuser user = (Sysuser) session.getAttribute("loginUser");
        if (null == user) {
            Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
            map.put("userId", new Integer((String) params.get("id")));
        }
        else {
            map.put("userId", user.getId());
        }
        try {
            if (null == map.get("id") || ((String) map.get("id")).equals("0")) {
                map.put("createTime", System.currentTimeMillis());
                flag = filtrationServiceImpl.save(map);
            }
            else {
                flag = filtrationServiceImpl.update(map);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return flag + "";
    }

    /***
     * 删除
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/deleteFiltration")
    @ResponseBody
    public String deleteFiltration(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = filtrationServiceImpl.delete(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return offset + "";
    }

    /**
     * 删除--多选
     * 
     * @return
     */
    @RequestMapping("/deleteFiltrations")
    @ResponseBody
    public String deleteFiltrations(@RequestParam Map<String, Object> params) {
        // 要删除的数据集，以字符串的形式存在，中间用逗号隔开
        String deleteids = params.get("ary").toString();
        // 获取到要删除id的数组
        String[] dellist = deleteids.split(",");
        // 调用删除方法，传入要删除id的数组
        return filtrationServiceImpl.deletes(dellist);
    }
}
