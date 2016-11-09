package com.bluemobi.pro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Groupinfo;
import com.bluemobi.pro.pojo.Groupliveness;
import com.bluemobi.sys.service.BaseService;

/**
 * 群活跃度service
 * 
 * @author gaolei
 */
@Service
public class GrouplivenessServiceImpl extends BaseService {

	public static String PREFIX = Groupliveness.class.getName();
	public static String PREFIX_GROUPINFO = Groupinfo.class.getName();
	/**
	 * 修改
	 * 
	 * @author gaolei
	 */
	public void update(Map<String, Object> params) throws Exception {
		this.getBaseDao().update(PREFIX + ".insert", params);
	}

	/**
	 * 根据groupId查询所有活跃的用户
	 * 
	 * @author gaolei
	 */
	public List<Map<String, Object>> getLivenessUsers(Map params)
			throws Exception {
		return this.getBaseDao().getList(PREFIX_GROUPINFO + ".queryUserMoneyInGroup", params);
	}

}