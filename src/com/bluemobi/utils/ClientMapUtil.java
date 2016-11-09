package com.bluemobi.utils;

import java.util.HashMap;
import java.util.Map;

public class ClientMapUtil {
	public static Map<String, Object> getResultMap(String status, String error, Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", status);
		result.put("error", error);
		result.put("data", data);
		return result;
	}
}
