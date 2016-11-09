package com.bluemobi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class StringLeftFunctions {
	/***
	 * 页面截取字符串
	 * 
	 * @author ZhaoZiYuan
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String StringLeft(String str, int strLength) {
		System.out.println(str.length());
		if (str.length() <= strLength) {
			return str;
		}
		// return StringUtils.left(str, strLength - 3) + "...";
		return StringUtils.left(str, strLength) + "...";
	}

	/***
	 * 页面URL传参中文转码
	 * 
	 * @author ZhaoZiYuan
	 * @param str
	 * @return
	 */
	public static String UriEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		System.out.println(StringLeftFunctions.StringLeft("你好", 5));
		System.out.println(StringUtils.left("abcde", 3));
	}
}
