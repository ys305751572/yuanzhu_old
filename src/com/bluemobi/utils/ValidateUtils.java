package com.bluemobi.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 判空
 * 
 * @author maew
 * 
 */
public class ValidateUtils {

	/**
	 * 验证对象非空
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		boolean isEmpty = false;
		if (obj == null) {
			isEmpty = true;
		} else if (obj instanceof String) {
			isEmpty = ((String) obj).trim().isEmpty();
		} else if (obj instanceof Collection) {
			isEmpty = (((Collection) obj).size() == 0);
		} else if (obj instanceof Map) {
			isEmpty = ((Map) obj).size() == 0;
		} else if (obj.getClass().isArray()) {
			isEmpty = Array.getLength(obj) == 0;
		}
		return isEmpty;
	}
	
	public static boolean isZHIXIA(String provinceId){
		if(provinceId != null &&(provinceId.equals("1") || provinceId.equals("2") || provinceId.equals("3") || provinceId.equals("4"))){
			return true;
		}else{
			return false;
		}
	}
}
