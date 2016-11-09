package com.bluemobi.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 客户端上传进度条信息
 * @author superman
 *
 */
public class UploadProgressUtils {
	
	/**
	 * 输出进度条信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void outPrintProgres(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int status = Integer
				.parseInt(session.getAttribute("read") == null ? "0" : session
						.getAttribute("read").toString());// 获取上传进度百分比
		if (status == 100) {
			session.removeAttribute("read");
		}
		out.println(status);// 响应
	}

}
