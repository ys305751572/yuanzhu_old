package com.bluemobi.pro.controller.webservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.service.impl.ReportServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.utils.MapUtil;

/**
 * 举报 controller
 * 
 * @author tutu
 */
@Controller
@RequestMapping(value = "/api")
public class ReportWBController extends BaseController {

    @Autowired
    public ReportServiceImpl reportServiceImpl;

    /**
     * 新增举报信息
     * 
     * @param map
     *            请求参数,参数如下： content 举报内容; id 举报人ID
     * @return json格式信息
     * @author tutu
     */
    @RequestMapping(value = "/report/create", method = RequestMethod.POST)
    @ResponseBody
    public Map create(@RequestParam Map<String, Object> params) {
        try {
            // 将数据存入本地数据库
            params.put("createTime", System.currentTimeMillis());
            int result = reportServiceImpl.save(params);

            if (result == 1) {
                // 叶松 2014-11-26
                jsonString = MapUtil.parse(Constant.STATUS_OK, Constant.STATUS_OK);
                // jsonString = JsonUtils.returnMsg(Constant.STATUS_OK,
                // Constant.MSG_OK);
            }
            else {
                // 叶松 2014-11-26
                jsonString = MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_08);
                // jsonString = JsonUtils.returnMsg(Constant.STATUS_KO,
                // Constant.ERROR_08);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            // 叶松 2014-11-26
            jsonString = MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
            // jsonString = JsonUtils.returnMsg(Constant.STATUS_KO,
            // Constant.ERROR_01);
        }
        return jsonString;
    }

}
