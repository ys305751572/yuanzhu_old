package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Users;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.LoginUser;
import com.bluemobi.sys.service.BaseService;
import com.bluemobi.utils.CommonUtils;

/**
 * @author TuYiHeng
 * 
 */
@Service
public class UsersServiceImpl extends BaseService {

    public static String PREFIX = Users.class.getName();

    public Users authUser(LoginUser credentials) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", credentials.getName());
        params.put("password", credentials.getPassword());
        params.put("usertype", 1);

        @SuppressWarnings("rawtypes")
        Map map = this.getBaseDao().getObject(PREFIX + ".find", params);
        Users user = null;
        if (null != map && map.size() > 1) {
            try {
                user = (Users) CommonUtils.convertMapToPOJO(Users.class, map);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public Page<List<Users>> list(Map<String, Object> params, int current, int pagesize) {
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }

    public Map<String, Object> updateuser(int userid, String pwd) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usersid", userid);
        map.put("password", pwd);
        int count = this.getBaseDao().update(PREFIX + ".update", map);
        map.clear();
        if (count > 0) {
            map.put("data", "1");
        }
        else {
            map.put("data", "0");
        }
        return map;
    }

    public Map<String, Object> deleteuser(int userid) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", userid);
        int count = this.getBaseDao().delete(PREFIX + ".delete", params);
        Map<String, Object> map = new HashMap<String, Object>();
        if (count > 0) {
            map.put("data", "1");
        }
        else {
            map.put("data", "0");
        }
        return map;
    }

    public int adduser(Map<String, String> params) throws Exception {
        return this.getBaseDao().save(PREFIX + ".insert", params);
    }

    public int updateuser(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    public int updatePassword(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".updatePassword", params);
    }

    public Users register(Map<String, Object> params) throws Exception {
        Users user = new Users();

        this.getBaseDao().save(PREFIX + ".insert", user);
        return user;
    }

    public Users getUsersById(int id) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.getBaseDao().getObject(PREFIX + ".get", params);
    }

    public Users checkRegister(String name) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deviceno", name);
        List<Users> map = this.getBaseDao().getList(PREFIX + ".find", params);
        Users user = null;
        if (null != map && map.size() == 1) {
            try {
                user = (Users) (map.get(0));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * 前台登录一系列校验
     * 
     * @throws Exception
     */
    public Map<String, Object> getToLogin1(Map<String, Object> params) throws Exception {
        Map<String, Object> retmap = new HashMap<String, Object>();
        // 调用dao层查询语句

        Map<String, Object> jdbcmap = this.getBaseDao().get(PREFIX + ".findToLogin1", params);
        if (jdbcmap == null || jdbcmap.size() == 0) {// 用户名错误
            retmap.put("status", "1");
            retmap.put("msg", "error_02");
            retmap.put("data", "");
        }
        else {// 用户存在验证其他信息
            if (params.get("password").equals(jdbcmap.get("password"))) {// 密码正确
                if (((String) jdbcmap.get("fstatus")).equals("0")) {// 审核通过
                    if (params.get("accessToken").equals(jdbcmap.get("deviceno"))) {// 设配号匹配
                        Map<String, Object> infomap = new HashMap<String, Object>();
                        infomap.put("info", jdbcmap);
                        retmap.put("status", "0");
                        retmap.put("msg", "success");
                        retmap.put("data", infomap);
                    }
                    else {// 设配号匹配
                        retmap.put("status", "1");
                        retmap.put("msg", "error_03");
                        retmap.put("data", "");
                    }
                }
                else {// 审核未通过
                    retmap.put("status", "1");
                    retmap.put("msg", "error_15");
                    retmap.put("data", "");
                }
            }
            else {// 密码错误
                retmap.put("status", "1");
                retmap.put("msg", "error_02");
                retmap.put("data", "");
            }
        }
        return retmap;
    }

    public Users getAccountById(Map<String, Object> params) throws Exception {
        List<Users> map = this.getBaseDao().getList(PREFIX + ".getAccountById", params);
        Users user = null;
        if (null != map && map.size() > 0) {
            try {
                user = (Users) (map.get(map.size() - 1));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public Users getMaxAccountNum(Map<String, Object> params) throws Exception {
        List<Users> map = this.getBaseDao().getList(PREFIX + ".findMaxAccountNum", params);
        Users user = null;
        if (null != map && map.size() == 1) {
            try {
                user = (Users) (map.get(0));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Users getRegisterUser(Map<String, Object> params) throws Exception {
        Map map = this.getBaseDao().get(PREFIX + ".findRegisterUser", params);
        Users user = null;
        if (null != map && map.size() > 0) {
            try {
                user = (Users) CommonUtils.convertMapToPOJO(Users.class, map);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public Users getRegAccountNum(Map<String, Object> params) throws Exception {
        List<Users> map = this.getBaseDao().getList(PREFIX + ".findRegUsers", params);
        Users user = null;
        if (null != map && map.size() == 1) {
            try {
                user = (Users) (map.get(0));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

	public Map getUsersByLoginName(String string) {
		try {
			return this.getBaseDao().get(PREFIX + ".getUsersByLoginName", string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}