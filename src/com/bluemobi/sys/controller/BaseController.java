package com.bluemobi.sys.controller;

import java.util.List;
import java.util.Map;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Page;
import com.bluemobi.pro.pojo.ValidataCode;
import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.utils.MapUtil;

/**
 * 
 * 
 * <p>
 * Title: BaseController.java
 * </p>
 *    
 * <p>
 * Description: controller基类
 * </p>
 *    @author yesong   @date 2014-11-13 下午03:18:47  @version V1.0 
 * ------------------------------------ 历史版本
 * 
 */
public class BaseController {

    public static final String STATUS_SUCCESS = "0"; // 成功
    public static final String STATUS_FAIL = "1";// 失败

    public static final String MSG_NULL = "";
    public static final String DATA_NULL = null;
    
    public static final String DATA_NAME_NULL = "";

    // 返回状态默认是成功
    public String status = Constant.STATUS_OK;
    // 返回信息默认是成功
    public String msg = Constant.MSG_OK;
    private Object data = null;
    private static final String NULL_NAME = "";
    @SuppressWarnings("rawtypes")
	public Map jsonString = null; // 返回客户端的json字符串

    // 反正json page对象
    public Page page = null;

    @SuppressWarnings("rawtypes")
    protected void doResp(Object data, String name, Page page, String status, String msg) {
        if (data instanceof List) {
            List list = (List) data;
            if (page == null)
                page = new Page();
            // jsonString = JsonUtils.returnList(list, page, status, msg);
            jsonString = MapUtil.parse(list, page, status, msg);
        }
        else {
            // if(data == null) data = DATA_NULL;
            if (name == null)
                name = NULL_NAME;
            // jsonString = JsonUtils.returnObj(data, name, status, msg);
            jsonString = MapUtil.parse(data, name, status, msg);

        }
    }

    protected void doError() {
        this.doResp(DATA_NULL, null, null, STATUS_FAIL, Constant.ERROR_01);
    }

    // new 接口返回page对象
    public Page newPage(int currentPage, int totalNum, int totalPage) {
        page = new Page(currentPage, totalNum, totalPage);
        return page;
    }

    protected void doNull() {
        // TODO 如果查询的数据为空
    }

    @SuppressWarnings("rawtypes")
    protected void initPage(Map<String, Object> map, com.bluemobi.sys.page.Page page1) {
        page = new Page();
        page.setCurrentPage(Integer.parseInt(null != map.get("pageNum") ? (String) map
                .get("pageNum") : "1"));
        page.setTotalNum((int) page1.getTotal());
        page.setTotalPage(page1.getPageCount());
    }

    public Boolean validateCode(String code,String mobile) {
    	ValidataCode vdc = (ValidataCode) DataCache.getInstance().getValidataCodeByMobile(mobile);
    	System.out.println("==========================getVolidateCode()===========:"+ mobile + "===" +vdc.getVolidateCode());
    	return vdc.validateCode(code);
    }
    
    //  发送验证码 
    public Boolean sendSms(String mobile) {
    	ValidataCode vdc = (ValidataCode) DataCache.getInstance().getValidataCodeByMobile(mobile);
    	Boolean flag = vdc.sendSms(mobile);
    	DataCache.getInstance().putMap("code", vdc);
		return flag;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @SuppressWarnings("rawtypes")
	public Map getJsonString() {
        return jsonString;
    }

    @SuppressWarnings("rawtypes")
	public void setJsonString(Map jsonString) {
        this.jsonString = jsonString;
    }
}
