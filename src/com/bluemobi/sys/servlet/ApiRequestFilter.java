package com.bluemobi.sys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    	 String urls = filterConfig.getInitParameter("skipUrls");
         if (StringUtils.isNotBlank(urls)) {
             String temp[] = urls.split(",");
             List<String> list = new ArrayList<String>();

             for (String skipUrl : temp) {
                 if (StringUtils.isNotBlank(skipUrl)) {
                     skipUrl = "^" + skipUrl.replaceAll("\\*", ".*") + "$";
                     list.add(skipUrl);
                 }
             }
             SKIP_URLS = list.toArray(SKIP_URLS);
         }
    }


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI().toString();
        String contextPath = httpRequest.getContextPath();
        url = url.substring(contextPath.length());
        if (SKIP_URLS != null) {
            for (String skipUrl : SKIP_URLS) {
                Pattern pattern = Pattern.compile(skipUrl, Pattern.DOTALL);
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        
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
