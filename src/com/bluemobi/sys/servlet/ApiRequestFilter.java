package com.bluemobi.sys.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.bluemobi.constant.Constant;
/**
 * Created by wangbin on 2015/8/10.
 */
public class ApiRequestFilter implements Filter {

    private static String[] SKIP_URLS = new String[]{};

    public ApiRequestFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        
        Map<String,String[]> m = new HashMap<String,String[]>(httpRequest.getParameterMap());
        request = new ParameterRequestWrapper((HttpServletRequest)request, m);
        
        boolean allow = true;
        
        String error = request.getParameter("error");
        if(StringUtils.isNotBlank(error)) {
        	allow = false;
        }
        
        String token = request.getParameter("token");
        if(StringUtils.isBlank(token) || !token.equals(Constant.ACCESS_KEY)) {
        	allow = false;
        }
        if( !allow ) {
        	httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
           	return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
