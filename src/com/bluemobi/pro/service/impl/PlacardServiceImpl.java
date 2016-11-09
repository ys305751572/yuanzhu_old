package com.bluemobi.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.City;
import com.bluemobi.pro.pojo.Placard;
import com.bluemobi.pro.pojo.PlacardRecord;
import com.bluemobi.pro.pojo.Province;
import com.bluemobi.pro.pojo.Scs;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;

/**
 * @author TuYiHeng
 * 
 */
@Service
public class PlacardServiceImpl extends BaseService {

    public static String PREFIX = Placard.class.getName();
    public static String PRIFIX_RECORD = PlacardRecord.class.getName();

    public Page<List<Placard>> list(Map<String, Object> params, int current, int pagesize) {
    	if (params.get("provinceId") == null || params.get("provinceId").toString().length() == 0
                || params.get("provinceId").toString().equals("")
                || params.get("provinceId").toString().equals("-1")) {// 鏌ヨ鎵�湁鐪佷唤锛屽煄甯傚氨涓嶇敤鍒ゆ柇
            params.put("provinceId", null);
            params.put("cityId", null);
            params.put("schoolId", null);
        }
        else if (params.get("cityId") == null || params.get("cityId").toString().length() == 0
                || params.get("cityId").toString().equals("")
                || params.get("cityId").toString().equals("-1")) {// 鐪佷唤纭畾锛屽煄甯傛湭鐭ワ紝鏌ヨ璇ョ渷浠戒笅鐨勬墍鏈夊煄甯傜殑瀛︽牎
            params.put("cityId", null);
            params.put("schoolId", null);
        }
        else if (params.get("schoolId") == null || params.get("schoolId").toString().length() == 0
                || params.get("schoolId").toString().equals("")
                || params.get("schoolId").toString().equals("-1")) {// 鐪佷唤纭畾锛屽煄甯傜‘瀹氾紝瀛︽牎鏈煡锛屾煡璇㈣鐪佷唤涓嬬殑璇ュ煄甯傜殑涓嬬殑鎵�湁瀛︽牎
            params.put("schoolId", null);
        }
        return this.getBaseDao().page(PREFIX + ".findAll", params, current, pagesize);
    }

    public Page<List<Placard>> list1(Map<String, Object> params, int current, int pagesize) {
    	if(params.get("userId") != null) {
    		try {
    			Map<String,Object> params2 = new HashMap<String,Object>();
    			params2.put("id", params.get("userId"));
				Map<String,Object> resultMap = this.getBaseDao().getObject("com.bluemobi.pro.pojo.StuUser" + ".findOne", params2);
				if(resultMap != null && resultMap.get("schoolId") != null) {
					params.put("userSchoolId", resultMap.get("schoolId"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        return this.getBaseDao().page(PREFIX + ".find", params, current, pagesize);
    }
    
    public int add(Map<String, Object> params) throws Exception {
        params.put("createTime", System.currentTimeMillis());
        return this.getBaseDao().save(PREFIX + ".insert", params);
    }

    public int delete(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".delete", params);
    }

    public String delete(String[] dellist) {
        int type = 0;
        Map<String, Object> params = null;
        for (int i = 0; i < dellist.length; i++) {
            params = new HashMap<String, Object>();
            params.put("id", dellist[i]);
            try {
                type += this.getBaseDao().update(PREFIX + ".delete", params);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type + "";
    }

    public int update(Map<String, Object> params) throws Exception {
        params.put("createTime", System.currentTimeMillis());
        return this.getBaseDao().update(PREFIX + ".update", params);
    }

    public Placard getById(String id) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.getBaseDao().getObject(PREFIX + ".get", params);
    }

    public Map<String, Object> findOneById(String id) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    public List<Province> findProvinceByParams(Map<String, Object> params) {
        String id = (String) params.get("id");
        if (id == "" || id == "0") {
            id = null;
        }
        params.put("id", id);
        try {
            return this.getBaseDao().getList(PREFIX + ".findProvince", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<City> findCityByParams(Map<String, Object> params) {
        String id = (String) params.get("id");
        if (id == "" || id == "0") {
            id = null;
        }
        params.put("id", id);
        try {
            return this.getBaseDao().getList(PREFIX + ".findCity", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Scs> findSchoolByParams(Map<String, Object> params) {
        if (params.get("provinceId") == null || params.get("provinceId").toString().length() == 0
                || params.get("provinceId").toString().equals("")
                || params.get("provinceId").toString().equals("-1")) {// 查询所有省份，城市就不用判断
            params.put("provinceId", null);
            params.put("cityId", null);
            params.put("id", -1);
        }
        else if (params.get("cityId") == null || params.get("cityId").toString().length() == 0
                || params.get("cityId").toString().equals("")
                || params.get("cityId").toString().equals("-1")) {// 城市未知，查询该省份下的所有城市的网点
            params.put("provinceId", null);
            params.put("cityId", null);
            params.put("id", -1);
        }
        else if (null == params.get("id") || "" == params.get("id")
                || params.get("id").equals("-1")) {
            params.put("id", null);
        }
        params.put("id", null);
        try {
        	if(params.get("provinceId")!=null && ( params.get("provinceId").toString().equals("1") || params.get("provinceId").toString().equals("2") || params.get("provinceId").toString().equals("3") || params.get("provinceId").toString().equals("4"))){
        		params.put("cityId",params.get("provinceId"));
        	}
        	 return this.getBaseDao().getList(PREFIX + ".findScs", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Scs> findCollegeByParams(Map<String, Object> params) {
        if (params.get("provinceId") == null || params.get("provinceId").toString().length() == 0
                || params.get("provinceId").toString().equals("")
                || params.get("provinceId").toString().equals("-1")) {// 查询所有省份，城市就不用判断
            params.put("provinceId", null);
            params.put("cityId", null); 
            params.put("id", -1);
        }
        else if (params.get("cityId") == null || params.get("cityId").toString().length() == 0
                || params.get("cityId").toString().equals("")
                || params.get("cityId").toString().equals("-1")) {// 城市未知，查询该省份下的所有城市的网点
            params.put("provinceId", null);
            params.put("cityId", null);
            params.put("id", -1);
        }
        else if (null == params.get("id") || "" == params.get("id")
                || params.get("id").equals("-1")) {
            params.put("id", null);
        }
        try {
        	if(params.get("provinceId")!=null && ( params.get("provinceId").toString().equals("1") || params.get("provinceId").toString().equals("2") || params.get("provinceId").toString().equals("3") || params.get("provinceId").toString().equals("4"))){
        		params.put("cityId",params.get("provinceId"));
        	}
        	 return this.getBaseDao().getList(PREFIX + ".findScs", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询咨询阅读记录
     * @param params
     * @param current
     * @param pagesize
     * @return
     */
    public Page<PlacardRecord> findPlacardRecordPage(Map<String,Object> params ,int current, int pagesize) {
    	return this.getBaseDao().page(PRIFIX_RECORD + ".findPage", params, current, pagesize);
    }
    
    /**
     * 新增阅读记录
     * @param params
     * @throws Exception
     */
    public void insertPlacardRecord(Map<String,Object> params) throws Exception {
    	this.getBaseDao().save(PRIFIX_RECORD + ".insertPlacardRecord", params);
    }
}