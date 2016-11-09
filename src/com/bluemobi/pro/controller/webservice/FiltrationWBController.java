package com.bluemobi.pro.controller.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.service.impl.FiltrationServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.sys.page.Page;
import com.bluemobi.utils.MapUtil;
import com.bluemobi.utils.PageUtiles;

/**
 * 过滤 controller
 * 
 * @author tutu
 */
@Controller
@RequestMapping(value = "/api")
public class FiltrationWBController extends BaseController {

    @Autowired
    public FiltrationServiceImpl filtrationServiceImpl;

    /**
     * 查询群列表信息
     * 
     * @return json格式信息
     * @author tutu
     */
    @RequestMapping(value = "/filtration/getFiltrationList", method = RequestMethod.POST)
    @ResponseBody
    public Map queryFiltrationList() {
        try {
            Page<Map<String, Object>> sysPage = filtrationServiceImpl.getFiltrationList(null, 1,
                    1000000);
            List<Map<String, Object>> oList = (List<Map<String, Object>>) sysPage.getRows();
            newPage(1, (int) sysPage.getTotal(), sysPage.getPageCount());

            // 去掉返回值中的content
            List<String> list = null;
            if (null != oList && oList.size() > 0) {
                list = new ArrayList<String>();
                Map<String, Object> map = null;
                for (int i = 0; i < oList.size(); i++) {
                    map = oList.get(i);
                    list.add((String) map.get("content"));
                }
            }
            // 叶松 2014-11-26
            jsonString = MapUtil.parse(list, page, Constant.STATUS_OK, Constant.MSG_OK);
            // jsonString = JsonUtils.returnList(list, page, Constant.STATUS_OK,
            // Constant.MSG_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.doError();
            // 叶松 2014-11-26
            // jsonString = JsonUtils.returnMsg(Constant.STATUS_KO,
            // Constant.ERROR_01);
        }
        return jsonString;
    }
}
