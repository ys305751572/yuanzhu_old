package com.bluemobi.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

public class YqssUtils {

	public static final int LEAP_YEAR_DAYS = 366;
	public static final int NO_LEAP_YEAR_DAYS = 365;

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将json格式的字符串解析成Map对象
	 * <li>json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> jsontoMap(Object object) {
		Map<String, Object> data = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = (Object) jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}
}
