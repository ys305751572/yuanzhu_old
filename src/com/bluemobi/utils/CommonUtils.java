/**
 * 
 */
package com.bluemobi.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @see 公共工具类
 * @author ChengYu
 * 
 */
public class CommonUtils {

	private static Properties properties = null;

	static {
		if (properties == null) {
			properties = new Properties();
		}
	}

	/**
	 * 将对象转换成POJO类
	 * 
	 * @param type
	 * @param map
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static Object convertMapToPOJO(Class<?> type, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate(String fomater) {
		return new Date();
	}

	public static String mapToXml(Map<String, Object> map) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<xml>");

		for (Map.Entry<String, Object> entity : map.entrySet()) {
			String key = entity.getKey();
			Object obj = entity.getValue();

			buffer.append("<").append(key).append(">").append(obj.toString()).append("</").append(key).append(">");
		}
		buffer.append("</xml>");
		return buffer.toString();
	}

	/**
	 * 生成订单号
	 */
	public static String generateSn() {
		Random r = new Random();
		int random = r.nextInt(1000);
		long current = System.currentTimeMillis();
		return String.valueOf(current) + String.valueOf(random);
	}
}