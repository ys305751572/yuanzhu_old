package com.bluemobi.pro.controller;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.pro.service.impl.CoinlogServiceImpl;
import com.bluemobi.sys.page.Page;

/**
 * @author TuYiHeng
 * 
 */
@Controller
public class CoinlogController {

    @Autowired
    private CoinlogServiceImpl coinlogServiceImpl;

    // ****************************框架外代码*********************************

    /***
     * 获取首页列表数据
     * 
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/allcoinlog")
    @ResponseBody
    public Page<List<Coinlog>> allcoinlog(@RequestParam Map<String, Object> map,HttpSession session) {
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
        Page<List<Coinlog>> page = coinlogServiceImpl.list(map, pages, rows);
        return page;
    }

    //获取充值总额 叶松 2015-2-9
    @RequestMapping("/allmoney")
    @ResponseBody
    public String allMoney() {
    	double totalMoney = 0.0;
 		try {
 			totalMoney = coinlogServiceImpl.getAllMoney();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
        DecimalFormat format = new DecimalFormat("#.00");
        return format.format(totalMoney) + ""; 
    }
    
    @RequestMapping("/coinloglist")
    public String coinloglist() {
        return "main/coinlog/coinloglist";
    }
}
