package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Feedback;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.CookiesUtils;

/**
 * @author TuYiHeng
 * 
 */
@Service
public class FeedbackServiceImpl extends BaseService {

    public static String PREFIX = Feedback.class.getName();

    public Page<List<Feedback>> list(Map<String, Object> params, int current, int pagesize) {
        if (null == params.get("status") || params.get("status").equals("")
                || params.get("status").equals("0")) {
            params.put("status", null);
        }
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }

    public int updateback(Map<String, Object> params) throws Exception {
        params.put("status", "2");
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    public int deleteback(Map<String, Object> params) throws Exception {
        return this.getBaseDao().delete(PREFIX + ".delete", params);
    }

    public Map<String, Object> getFeedbackById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".get", params);
    }

    public String updates(String[] list, HttpSession session, HttpServletRequest request) {
        int type = 0;
        // 获取当前用户
        Sysuser user = (Sysuser) session.getAttribute("loginUser");
        Map<String, Object> params = null;
        for (int i = 0; i < list.length; i++) {
            params = new HashMap<String, Object>();
            params.put("id", list[i]);
            if (null == user) {
//                Map<String, Object> map = CookiesUtils.ReadCookieMap(request);
//                params.put("stuUserId", new Integer((String) map.get("id")));
            }
            else {
//                params.put("stuUserId", user.getId());
            }
            params.put("status", "2");
            params.put("backTime", System.currentTimeMillis());
            try {
                type += this.getBaseDao().update(PREFIX + ".update", params);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type + "";
    }
}