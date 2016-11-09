package com.bluemobi.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bluemobi.constant.Constant;
import com.bluemobi.sys.pojo.LoginUser;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.sys.service.SysuserService;
import com.bluemobi.utils.CookiesUtils;
import com.bluemobi.utils.SessionUtils;

/**
 * 
 * @author liu_shi_lin
 * @Date 2014-11-1 下午10:31:22
 */
@Controller
public class SysuserController {

    @Autowired
    private SysuserService sysuserService;

    /**
     * 跳转到登陆页面
     */

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@ModelAttribute("credentials") LoginUser credentials,
            HttpServletRequest request) {
        Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
        if (params != null && params.size() != 0 && null != params.get("id")
                && "" != (String) params.get("id") && "0" != (String) params.get("id")) {
            LoginUser logUser = new LoginUser();
            logUser.setId(new Integer((String) params.get("id")));
            logUser.setName((String) params.get("name"));
            logUser.setPassword((String) params.get("password"));
            Sysuser user = null;
            try {
                user = sysuserService.getLogin(logUser, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (user != null) {
                SessionUtils.put(Constant.CURRENT_USER, user);
                return "redirect:/index";
            }
        }
        return "login";

    }

    /**
     * 执行登陆操作
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String auth(@ModelAttribute("credentials") LoginUser loginUser, Model model,
            HttpServletRequest request, HttpServletResponse response, HttpSession session,
            String flag) {
            try {
                Sysuser user = sysuserService.getLogin(loginUser, false);
                session.setAttribute("loginUser", user);

                // 将用户名和密码写入cookies
                int loginMaxAge = 30 * 24 * 60 * 60; // 定义账户密码的生命周期，这里是一个月。单位为秒
                if (null != flag && flag.equals("true")) {
                    CookiesUtils.addCookie(response, "id", user.getId().toString(), loginMaxAge);
                    CookiesUtils.addCookie(response, "name", user.getName(), loginMaxAge);
                    CookiesUtils.addCookie(response, "password", user.getPassword(), loginMaxAge);
                }

                if (user == null) {
                    loginUser.setPassword(null);
                    session.setAttribute("message", "用户名密码错误");
//                    model.addAttribute("message", "用户名密码错误");
                }
                SessionUtils.put(Constant.CURRENT_USER, user);
                return "redirect:/index";
            }
            catch (Exception e) {
                e.printStackTrace();
                loginUser.setPassword(null);
                model.addAttribute("message", "登录失败");
            }
        return "login";
    }

    /**
     * 执行登出操作
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus status, HttpServletRequest request,
            HttpServletResponse response) {
        status.setComplete();
        SessionUtils.clear();
        Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
        if (params != null && params.size() != 0) {
            // 删除cookie
            CookiesUtils.logoutCookie(request, response);
        }
        return "redirect:/index";
    }

    /**
     * 后台用户注册
     * 
     * @param params
     * @return
     * @author liu_shi_lin
     * @Date 2014-11-1 下午10:30:38
     */
    @RequestMapping("/register")
    @ResponseBody
    public int registerSysuser(@RequestParam Map<String, Object> params) {
        int i = -1;
        try {
            i = sysuserService.insertSysuser(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

}
