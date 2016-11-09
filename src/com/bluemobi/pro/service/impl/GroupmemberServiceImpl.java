package com.bluemobi.pro.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Groupmember;
import com.bluemobi.sys.service.BaseService;

/**
 * GroupmemberServiceImpl
 * 
 * @author gaolei
 */
@Service
public class GroupmemberServiceImpl extends BaseService {

    public static String PREFIX = Groupmember.class.getName();

    /**
     * 添加成员
     * 
     * @author gaolei
     */
    public void addMember(Map<String, Object> params) throws Exception {
    	Object userIds = params.get("userId");
    	if(userIds instanceof Integer)
    	{
    		int userId = (Integer)userIds;
    		params.put("stuUserId",userId);
    		if (this.getBaseDao().getObject(PREFIX + ".findOne", params)==null) {
	    		this.getBaseDao().save(PREFIX + ".insert", params);
			}
    	}
    	else if(userIds instanceof String)
    	{
    		String ids = (String) userIds ;
    		if(StringUtils.isNotBlank(ids)){
        		String[] ids1 = ids.split(",");
        		for (String userId : ids1) {
        			params.put("stuUserId",userId);
        	    	if (this.getBaseDao().getObject(PREFIX + ".findOne", params)==null) {
        	    		this.getBaseDao().save(PREFIX + ".insert", params);
        			}
    			}
        	}
    	}
    	
    }
    
    /**
     * 群删除成员
     * 
     * @author gaolei
     */
    public int removeMember(Map<String, Object> params) throws Exception {
    	params.put("stuUserId", params.get("userId"));
    	return this.getBaseDao().delete(PREFIX + ".delete", params);
    }
    
    /**
     * 查询
     * 
     * @author gaolei
     */
    public Map<String, Object> findOne(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    /**
     * 删除
     * 
     * @author gaolei
     */
    public int delete(Map<String, Object> params) throws Exception {
        return this.getBaseDao().delete(PREFIX + ".delete", params);
    }
}