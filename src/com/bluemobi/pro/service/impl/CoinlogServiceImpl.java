package com.bluemobi.pro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Coinlog;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;

/**
 * coinlog impl
 * 
 * @author gaolei
 */
@Service
public class CoinlogServiceImpl extends BaseService {

    public static String PREFIX = Coinlog.class.getName();

    public int save(Coinlog coinlog) throws Exception {
        return this.getBaseDao().save(PREFIX + ".insert", coinlog);
    }

    public int delete(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".delete", params);
    }

    public Map<String, Object> findOneById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    public int update(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    public Page<List<Coinlog>> list(Map<String, Object> params, int current, int pagesize) {
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }
    
    /** 获取重置总额(人民币) 
     * @throws Exception */
    public double getAllMoney() throws Exception {
    	return this.getBaseDao().getObject(PREFIX + ".getAllMoney", null);
    }
}