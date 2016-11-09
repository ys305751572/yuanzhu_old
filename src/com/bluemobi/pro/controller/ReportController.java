package com.bluemobi.pro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Report;
import com.bluemobi.pro.service.impl.ReportServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class ReportController {

    @Autowired
    private ReportServiceImpl reportServiceImpl;

    /***
     * 获取列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allreport")
    @ResponseBody
    public Page<List<Report>> allreport(@RequestParam Map<String, Object> map) {
        Integer rows = new Integer((String) map.get("rows"));
        Integer pages = new Integer((String) map.get("page"));
        if (null == map.get("startDate") || "" == map.get("startDate")
                || map.get("startDate").toString().trim().length() == 0) {
            map.put("startDate", "1900-01-01");
        }
        if (null == map.get("endDate") || "" == map.get("endDate")
                || map.get("endDate").toString().trim().length() == 0) {
            map.put("endDate", "2030-12-29");
        }
        Page<List<Report>> page = reportServiceImpl.list(map, pages, rows);
        return page;
    }

    @RequestMapping("/reportlist")
    public String list() {
        return "main/report/reportlist";
    }
}
