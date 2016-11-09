package com.bluemobi.pro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Friend;
import com.bluemobi.sys.service.BaseService;

/**
 * friend impl
 * 
 * @author gaolei
 */
@Service
public class FriendServiceImpl extends BaseService {

    public static String PREFIX = Friend.class.getName();

    /**
     * 建立2个人的好友关系
     * @author gaolei
     */
    public void add(Map<String, Object> params) throws Exception {
    	//用户id
    	int stuUserId = Integer.parseInt((String) params.get("owner"));
    	//好友id
		int friendId = Integer.parseInt((String) params.get("friend"));
		
		List<Friend> fList = new ArrayList<Friend>();
		//添加正向关系
		Friend friend = new Friend();
		friend.setStuUserId(stuUserId);
		friend.setFriendId(friendId);
		fList = this.getBaseDao().getList(PREFIX + ".get", friend);
		if (fList.size()<=0) {
			this.getBaseDao().save(PREFIX + ".insert", friend);
		}
			
		//在好友方添加关系
		friend.setStuUserId(friendId);
		friend.setFriendId(stuUserId);
		fList = this.getBaseDao().getList(PREFIX + ".get", friend);
		if (fList.size()<=0) {
			this.getBaseDao().save(PREFIX + ".insert", friend);
		}
    }

    public int delete(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".delete", params);
    }

    public Map<String, Object> findOneById(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".findOne", params);
    }

    public Map<String, Object> findOne(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getObject(PREFIX + ".find", params);
    }
    
    public int update(Map<String, Object> params) throws Exception {
        return this.getBaseDao().update(PREFIX + ".update", params);
    }
    
    public List<String> getFriendIdList(Map<String, Object> params) throws Exception {
        return this.getBaseDao().getList(PREFIX + ".getFriendIdList", params);
    }
    
    /**
     * 修改好友备注
     * @param params
     * @throws Exception
     */
    public void updateFnote(Map<String,Object> params) throws Exception {
    	this.getBaseDao().update(PREFIX + ".updatefnote", params);
    }
}