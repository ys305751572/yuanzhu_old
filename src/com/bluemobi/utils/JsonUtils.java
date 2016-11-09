package com.bluemobi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bluemobi.pro.pojo.Page;
import com.bluemobi.pro.pojo.StuUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Json工具类
 * 
 * @author TuTu
 * 
 */
public class JsonUtils {
	public static void main(String[] args) {
		String a = returnObj(null, null, "1", "1");
		System.out.println(a);
	}

    /**
     * 把json对象串转换成map对象
     * 
     * @param jsonObjStr
     *            e.g. {'name':'get','int':1,'double',1.1,'null':null}
     * @return Map
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map getMapFromJsonObjStr(String jsonObjStr) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
            String key = (String) iter.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    public static String toJson(Object obj) {
    	JSONArray array = JSONArray.fromObject(obj);
    	 return array.toString();
    }
    
    /**
     * 把map对象串转换成json对象
     * 
     * @param map
     * @return jsonObjStr e.g. {'name':'get','int':1,'double',1.1,'null':null}
     */
    public static String toJson(Map<String, Object> map) {
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }

    /**
     * @Description 把list对象转换成json对象
     * @return 
				{
				    "status": "11",
				    "data": {
				        "result": [
				            {
				                "mobile": "13554545555",
				                "name": "Tom"
				            }
				            {
				                "mobile": "13554545555",
				                "name": "Jerry"
				            }
				        ],
				        "page": {
				            "currentPage": "1",
				            "totalNum": "20",
				            "totalPage": "12"
				        }
				    },
				    "msg": "error_01"
				}
				
     * @date 2014-10-16 13:11:04
     * @author 龙哲
     */
    @SuppressWarnings("unchecked")
	public static String returnList(List list, Page page, String status,
            String msg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        // "status": "1"
        returnMap.put("status", status);
        // "msg": "error_01"
        returnMap.put("msg", msg);
        // result对象
        dataMap.put("list", null == list ? new ArrayList() : list);
        // page对象
        dataMap.put("page", page);
        // result和page对象放到data里
        returnMap.put("data", dataMap);
        
        JSONObject json = JSONObject.fromObject(returnMap);
        return json.toString();
    }

    /**
     * @Description 把list对象转换成json对象
     * 
     * @param list
     * @return
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    public static String returnList(List list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
    }

    /**
     * @Description 把object对象转换成json对象
     * 
     * @param obj
     * @return
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    public static String returnObject(Object obj) {
        JSONArray jsonArray = JSONArray.fromObject(obj);
        return jsonArray.toString();
    }

    /**
     * @Description 把数组转换成json对象
     * 
     * @param arr
     * @return
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    public static String returnStrings(String[] arr) {
        JSONArray jsonArray = JSONArray.fromObject(arr);
        return jsonArray.toString();
    }

    /**
     * @Description 把obj对象转换成json对象
     * @param 参数列表
     *            Page page 存放;
     * @return
     * @date 2014-10-16 13:11:04
     * @author 龙哲
     */
    public static String returnObj(Object obj, String objName, String status, String msg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // "status": "1"
        returnMap.put("status", status);
        // "msg": "000000"
        returnMap.put("msg", msg);
        // resultMap
        if (StringUtils.isNotEmpty(objName) && obj!=null) {
            resultMap.put(objName, obj);
        }
        returnMap.put("data", resultMap);
        JSONObject json = JSONObject.fromObject(returnMap);
        return json.toString();
    }
    
    /**
     * 返回状态信息
     * 当仅有status,msg,	data为空时调用
     * @return json格式信息 {"status":"1","data":{},"msg":"00001"}
     * @author gaolei
     */
    public static String returnMsg(String status,String msg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        returnMap.put("status", status);
        returnMap.put("msg", msg);
        returnMap.put("data", dataMap);
        JSONObject json = JSONObject.fromObject(returnMap);
        return json.toString();
    }
    
	 /**
	  * 从一个JSON 对象字符格式中得到一个java对象
	  * @param jsonString
	  * @param pojoCalss
	 */
	 public static Object getObject4JsonString(String jsonString,Class pojoCalss){
		 Object pojo;
		 JSONObject jsonObject = JSONObject.fromObject( jsonString ); 
		 pojo = JSONObject.toBean(jsonObject,pojoCalss);
		 return pojo;
	 }
	 
	 @SuppressWarnings("unchecked")
	public static Map<String,Object> getMapFromPojo(Object obj) {
		 String josn = JSONArray.fromObject(obj).toString();
		 Map<String,Object> map = getMapFromJsonObjStr(josn.substring(1, josn.length() - 1));
		 return map;
	 }
}