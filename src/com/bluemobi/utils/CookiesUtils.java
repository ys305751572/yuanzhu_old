package com.bluemobi.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtils {

    /**
     * 设置cookie（接口方法）
     * 
     * @param response
     * @param name
     *            cookie名字
     * @param value
     *            cookie值
     * @param maxAge
     *            cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0)
            cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 将cookie封装到Map里面（非接口方法）
     * 
     * @param request
     * @return 返回之后走自动登陆流程
     */
    public static Map<String, Object> ReadCookieMap(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                String attname = cookie.getName();
                if (attname.equals("name")) {
                    params.put("name", cookie.getValue());
                }
                else if (attname.equals("password")) {
                    params.put("password", cookie.getValue());
                }
                else if (attname.equals("id")) {
                    params.put("id", cookie.getValue());
                }
            }
        }
        return params;
    }

    /**
     * 主动退出删除cookie
     * 
     * @param request
     * @return
     * @author liu_shi_lin
     * @Date 2014-10-24 下午4:19:32
     */
    public static void logoutCookie(HttpServletRequest request, HttpServletResponse response) {
        /*
         * CookiesUtils.addCookie(response, "id", null, 0);
         * CookiesUtils.addCookie(response, "name", null, 0);
         * CookiesUtils.addCookie(response, "password", null, 0);
         */
        clearCookie(request, response, "/");
    }

    /**
     * 清空cookie
     * 
     * @param request
     * @param response
     * @param path
     * @return
     * @author tutu
     * @Date 2014-11-24 上午10:38:32
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response,
            String path) {
        Cookie[] cookies = request.getCookies();
        try {
            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].getName() + ":" + cookies[i].getValue());
                Cookie cookie = new Cookie(cookies[i].getName(), null);
                cookie.setMaxAge(0);
                cookie.setPath(path);// 根据你创建cookie的路径进行填写
                response.addCookie(cookie);
            }
        }
        catch (Exception ex) {
            System.out.println("清空Cookies发生异常！");
        }
    }
}