package com.bluemobi.sys.service;

import java.util.Map;

import com.bluemobi.sys.pojo.LoginUser;
import com.bluemobi.sys.pojo.Sysuser;

public interface SysuserService {

    /**
     * 后台用户注册
     * 
     * @param params
     * @return
     * @author liu_shi_lin
     * @throws Exception
     * @Date 2014-11-1 下午9:24:55
     */
    public int insertSysuser(Map<String, Object> params) throws Exception;

    /**
     * 后台用户登录
     * 
     * @param loginUser
     * @return
     * @author liu_shi_lin
     * @throws Exception
     * @Date 2014-11-1 下午9:39:10
     */
    public Sysuser getLogin(LoginUser loginUser, boolean flag) throws Exception;

}
