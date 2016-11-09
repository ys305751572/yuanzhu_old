package com.bluemobi.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List listToMap(List list) {
		List _list = new ArrayList();
		for (Object object : list) {
			_list.add(beanToMap(object));
		}
		return _list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static Map mapToMap(Map<String,Object> data) {
		for (Map.Entry<String,Object> entity : data.entrySet()) {
			Object obj = entity.getValue();
			if(obj instanceof List) {
				List list = new ArrayList();
				for (Object object : (List)obj) {
					list.add(beanToMap(object));
				}
				data.put(entity.getKey(),list);
			}
			else {
				data.put(entity.getKey(), beanToMap(entity.getValue()));
			}
			
		}
		return data;
	}
	
	public static Map<String,Object> beanToMap(Object data) {
		Assert.IsNotNull(data);
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = data.getClass().getDeclaredFields();
		try {
			
			PropertyDescriptor pd = null;
			for (Field field : fields) {
				String k = field.getName();
				Object v = null;
				pd = new PropertyDescriptor(k, data.getClass());
				Method getMethod = pd.getReadMethod();
				if(pd != null) {
					v = getMethod.invoke(data);
				}
				if(v != null) {
					map.put(k, v);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return map;
	}
}

