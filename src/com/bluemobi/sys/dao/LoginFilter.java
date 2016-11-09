package com.bluemobi.sys.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.bluemobi.constant.Constant;
import com.bluemobi.sys.pojo.Sysuser;

public class LoginFilter implements Filter{

	private List<String> noFilterList ;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		
		Pattern pattern = null;
		Matcher matcher = null;
		String url = req.getRequestURL().toString();
		if( noFilterList == null ) return;
		for (String noFilter : noFilterList) {
			pattern = Pattern.compile(noFilter);
			matcher = pattern.matcher(url);
			
			if( matcher.find() ) {
				chain.doFilter(request, response);
				return ;
			}
		}
		Sysuser user = (Sysuser) session.getAttribute(Constant.CURRENT_USER);
		if(user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
		else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		noFilterList = new ArrayList<String>();
		String noFilter = arg0.getInitParameter("noFilter");
		if(StringUtils.isNotBlank(noFilter)) {
			String[] ss = noFilter.split(",");
			for (String string : ss) {
				noFilterList.add(string);
			}
		}
	}
}
