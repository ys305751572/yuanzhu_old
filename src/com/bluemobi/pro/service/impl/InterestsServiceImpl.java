package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Interest;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;

/**
 * @author TuYiHeng
 * 
 */
@Service
public class InterestsServiceImpl extends BaseService {

    public static String PREFIX = Interest.class.getName();

    public Page<List<Interest>> list(Map<String, Object> params, int current, int pagesize) {
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }

    public int update(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    public int save(Map<String, Object> params) throws Exception {
    	
    	
        return this.getBaseDao().save(PREFIX + ".insert", params);
    }

    public int delete(Map<String, Object> params) throws Exception {
        return this.getBaseDao().delete(PREFIX + ".delete", params);
    }

    public Interest getById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".get", params);
    }

    public String deletes(String[] list) {
        int type = 0;
        Map<String, Object> params = null;
        for (int i = 0; i < list.length; i++) {
            params = new HashMap<String, Object>();
            params.put("id", list[i]);
            try {
                type += this.getBaseDao().delete(PREFIX + ".delete", params);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type + "";
    }
}