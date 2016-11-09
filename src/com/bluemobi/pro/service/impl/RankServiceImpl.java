package com.bluemobi.pro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.sys.service.BaseService;

@Service
public class RankServiceImpl extends BaseService{

	public static final String PRIFIX = RankServiceImpl.class.getName();
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 * @Description: 获取群排行列表
	 */
	public List<?> getGroupRankList(Map<String,Object> params) throws Exception {
		return this.getBaseDao().getList(PRIFIX + ".getGrank",params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 * @Description: 获取爱心排名
	 */
	public List<?> getSignRankList(Map<String,Object> params) throws Exception {
		List<Map<String,Object>> rankUsers = this.getBaseDao().getList(PRIFIX + ".getCrank", params);
		for (Map<String, Object> map : rankUsers) {
			Map schoolName = (Map) DataCache.getInstance().getObjectByCode("scs", "" + map.get("provinceId") + map.get("cityId") + map.get("schoolId"));
			Map collegeName = (Map) DataCache.getInstance().getObjectByCode("scs", "" + map.get("provinceId") + map.get("cityId") + map.get("collegeId"));
			
			map.put("schoolName", schoolName == null ? "" : schoolName.get("proName"));
			map.put("collegeName", collegeName == null ? "" : collegeName.get("proName"));
		}
		return rankUsers;
	}
}
