package com.bluemobi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.bluemobi.pro.pojo.Page;

/**
 * @author gaolei
 */
public class MapUtil {
	
	 /**
     * 单个Object封装为map
     * @return Map
     * @author gaolei
     */
    public static Map<String, Object> parse(Object obj, String objName, String status, String msg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        returnMap.put("status", status);
        returnMap.put("msg", msg);
        if (StringUtils.isNotEmpty(objName) && obj!=null) {
            resultMap.put(objName, obj);
        }
        returnMap.put("data", resultMap);
        return returnMap;
    }
    
    /**
     * List封装为map
     * @return Map
     * @author gaolei
     */
    public static Map<String, Object> parse(List list, Page page, String status,String msg) {
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
        return returnMap;
    }
    
    /**
     * Msg封装为map
     * @return Map
     * @author gaolei
     */
    public static Map<String, Object> parse(String status,String msg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        returnMap.put("status", status);
        returnMap.put("msg", msg);
        returnMap.put("data", dataMap);
        return returnMap;
    }
    
    /**
     * 解码
     * 
     * @author gaolei
     */
    public static Map _decode(Map<String, Object> params) throws UnsupportedEncodingException {
    	Map _new = new HashMap();
		for (Map.Entry<String, Object> obj : params.entrySet()) {
			String _value = (String) obj.getValue();
			String _key = (String) obj.getKey(); 
			if(StringUtils.isNotBlank(_value)){
				_value = URLDecoder.decode(_value, "UTF-8");
			}
			_new.put(_key, _value);
		}
		return _new;
	}
	
	public static <K, V> Map parseNullToEmpty(Map<K, V> map) {
		Set<K> keys = (Set<K>) map.keySet();
		for (K key: keys) { 
			if (map.get(key) == null || "null".equals(map.get(key))) {
				map.put(key, (V) "");
			}
		}
		return map;
	}
	
	public static <K, V> List parseNullToEmpty(List<Map<K, V>> list) {
		for (int i = 0; i < list.size(); i++) {
			parseNullToEmpty(list.get(i));
		}
		return list;
	}

	
	private static final double EARTH_RADIUS = 6378137; // 地球半径

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	/**
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 * @Description: 根据经纬度计算两点之间的距离
	 */
	public static double getDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(getDistance(114.365,23.258, 114.365, 23.258));
	}
}
