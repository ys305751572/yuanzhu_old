package com.bluemobi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

public class DecodeUtils {

	public static String DEFUALT_CHARSET = "UTF-8";

	public static String decode(String value, String charset) {

		String result = null;
		if (StringUtils.isBlank(charset)) {
			charset = DEFUALT_CHARSET;
		}
		if (StringUtils.isBlank(value)) {
			return null; 
		}
//		value = value.replaceAll("%", "%25");
		try {
			result = URLDecoder.decode(value, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String decode(String value) {
		return decode(value, null);
	}
	
	public static void main(String[] args) {
		System.out.println(DecodeUtils.decode("爱是范德萨%"));
	}
}
