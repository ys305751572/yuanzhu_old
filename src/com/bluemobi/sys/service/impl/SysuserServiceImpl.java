package com.bluemobi.sys.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.sys.dao.SysuserDao;
import com.bluemobi.sys.pojo.LoginUser;
import com.bluemobi.sys.pojo.Sysuser;
import com.bluemobi.sys.service.SysuserService;
import com.bluemobi.utils.CommonUtils;
import com.bluemobi.utils.MD5;

@Service
public class SysuserServiceImpl implements SysuserService {

    @Autowired
    private SysuserDao sysuserDao;

    /**
     * 后台用户注册
     * 
     * @param params
     * @return
     * @author liu_shi_lin
     * @throws Exception
     * @Date 2014-11-1 下午9:24:55
     */
    @Override
    public int insertSysuser(Map<String, Object> params) throws Exception {
        Sysuser sysuser = (Sysuser) CommonUtils.convertMapToPOJO(Sysuser.class, params);
        // sysuser.setPassword(MD5.md5(sysuser.getPassword()));
        return sysuserDao.insert(sysuser);
    }

    /**
     * 后台用户登录
     * 
     * @param loginUser
     * @return
     * @author liu_shi_lin
     * @throws Exception
     * @Date 2014-11-1 下午9:39:10
     */
    @Override
    public Sysuser getLogin(LoginUser loginUser, boolean flag) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", loginUser.getName());
        // 如果为true，就是cookies传过来的值
        if (flag) {
            map.put("password", loginUser.getPassword());
        }
        else {
            map.put("password", MD5.md5(loginUser.getPassword()));
        }
        return sysuserDao.getLogin(map);
    }

}
