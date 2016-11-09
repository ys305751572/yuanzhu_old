package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bluemobi.pro.pojo.Groupinfo;
import com.bluemobi.pro.pojo.Groupscale;
import com.bluemobi.pro.pojo.Grouptype;
import com.bluemobi.pro.service.impl.GroupServiceImpl;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.EasemobUtil;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class GroupinfoController {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    // ****************************框架外代码*********************************

    /***
     * 获取首页列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allgroup")
    @ResponseBody
    public Page<List<Groupinfo>> allgroup(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        Page<List<Groupinfo>> page = groupServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/grouplist")
    public String grouplist(@RequestParam Map<String, Object> map, Model model) {
    	model.addAttribute("pages", map.get("pages"));
        return "main/groupinfo/grouplist";
    }

    @RequestMapping("/groupShow")
    public String groupShow(@RequestParam Map<String, Object> map, Model model) {
        try {
            Map<String, Object> group = groupServiceImpl.findOneById(map);
            // 获取入群人数
            List<String> list = EasemobUtil.getGroupMembersIncludeOwner((String) group.get("id"));
            group.put("joincount", list.size());
            group.put("pageNumber", map.get("pageNumber"));
            model.addAttribute("group", group);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "main/groupinfo/groupshow";
    }

    /***
     * 处理信息
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/updateGroup")
    @ResponseBody
    public String updateGroup(@RequestParam Map<String, Object> map) {
        int offset = 0;
        try {
            offset = groupServiceImpl.update(map);
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
    @RequestMapping("/updateGroups")
    @ResponseBody
    public String updateGroups(@RequestParam Map<String, Object> params) {
        // 调用更新方法，传入要处理反馈id的数组
        return groupServiceImpl.updates(params);
    }

    /**
     * 根据条件查询群规模
     * 
     * @return
     */
    @RequestMapping("findScaleByParams")
    @ResponseBody
    public List<Groupscale> findScaleByParams(@RequestParam Map<String, Object> params) {
        List<Groupscale> li = null;
        try {
            li = groupServiceImpl.findScaleByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Groupscale groupscale = new Groupscale();
        groupscale.setId(-1);
        groupscale.setScale("所有规模");
        li.add(0, groupscale);
        return li;
    }

    /**
     * 根据条件查询群类型
     * 
     * @return
     */
    @RequestMapping("findTypeByParams")
    @ResponseBody
    public List<Grouptype> findTypeByParams(@RequestParam Map<String, Object> params) {
        List<Grouptype> li = null;
        try {
            li = groupServiceImpl.findTypeByParams(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Grouptype grouptype = new Grouptype();
        grouptype.setId(-1);
        grouptype.setName("所有类型");
        li.add(0, grouptype);
        return li;
    }
}
