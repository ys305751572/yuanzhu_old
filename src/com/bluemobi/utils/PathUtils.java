package com.bluemobi.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bluemobi.constant.Constant;

/**
 * 项目路径 操作辅助类
 * 
 * @author maew
 * 
 */
public class PathUtils {

	public static String getRemotePath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		String absPath = null;
		if(ra == null){
			absPath = PropertiesUtils.getPropertiesValues("jdbc.context", Constant.JDBC_PROP);
			return absPath;
		}
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		String scheme = request.getScheme();
		String host = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();
		absPath = scheme + "://" + host + (port != 80 ? ":" + port : "") + context;
		return absPath;
	}
}
