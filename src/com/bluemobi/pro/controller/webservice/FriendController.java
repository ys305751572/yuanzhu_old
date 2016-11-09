package com.bluemobi.pro.controller.webservice;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bluemobi.constant.Constant;
import com.bluemobi.pro.service.impl.FriendServiceImpl;
import com.bluemobi.pro.service.impl.StuUserServiceImpl;
import com.bluemobi.sys.controller.BaseController;
import com.bluemobi.utils.EasemobUtil;
import com.bluemobi.utils.MapUtil;

/**
 * 好友 controller
 * @author gaolei
 */
@Controller
@RequestMapping(value = "/api")
public class FriendController extends BaseController{
	@Autowired
	public StuUserServiceImpl stuUserServiceImpl;
	
	@Autowired
	public FriendServiceImpl friendServiceImpl;
	
	/**
	 * 添加好友
	 * @param map 请求参数,参数如下：
	 *		owner 用户id（必填）
	 *		friend 好友id（必填）
	 * @author gaolei
	 */
	@RequestMapping(value="/friend/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@RequestParam Map<String, Object> params) {
		try {
			friendServiceImpl.add(params);
			return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
		} 
	}
	
	/**
	 * 删除好友
	 * @param map 请求参数,参数如下：
	 *		owner 用户id（必填）
	 *		friend 好友id（必填）
	 * @author gaolei
	 */
	@RequestMapping(value="friend/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam Map<String, Object> params) {
		try {
			int stuUserId = Integer.parseInt((String) params.get("owner"));
			int friendId = Integer.parseInt((String) params.get("friend"));
			//是否从对方的列表把我删除
			String deleteFromFriend = (String) params.get("deleteFromFriend");
			
			//去环信删除好友关系
			String resp = EasemobUtil.deleteFriend(Constant.EASEMOB_ + stuUserId, Constant.EASEMOB_ + friendId);
			if (resp!=null) {
				params.put("stuUserId", stuUserId);
				params.put("friendId", friendId);
				friendServiceImpl.delete(params);
				if ("true".equals(deleteFromFriend)) {
					params.put("stuUserId", friendId);
					params.put("friendId", stuUserId);
					friendServiceImpl.delete(params);
				}
			}
			return MapUtil.parse(Constant.STATUS_OK, Constant.MSG_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
		} 
	}

	/**
	 * 查询好友列表
	 * @param map 请求参数,参数如下：
	 *		userId 用户id（必填）
	 *		nickname 好友昵称
	 *		pageNum 页码（必填）
	 *		pageSize 每页显示条数（必填）
	 * @author gaolei
	 */
	@RequestMapping(value="/friend/getList" , method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFriendList(@RequestParam Map<String, Object> params) {
		try {
	    	//返回list数据
			List<Map<String, Object>> list = stuUserServiceImpl.queryFriends(params);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> m = list.get(i);
				if (StringUtils.isBlank((String)m.get("nickname"))) {
					m.put("nickname", m.get("mobile"));
				}
			}
        	return MapUtil.parse(list, "list", Constant.STATUS_OK, Constant.MSG_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return MapUtil.parse(Constant.STATUS_KO, Constant.ERROR_01);
		} 
    }
	
	/**
	 * 修改用户备注
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/friend/updatefnote",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateFnote(@RequestParam Map<String,Object> params) {
		try {
			friendServiceImpl.updateFnote(params);
			this.doResp(DATA_NULL, DATA_NAME_NULL, null, STATUS_SUCCESS, MSG_NULL);
		} catch (Exception e) {
			e.printStackTrace();
			this.doError();
		}
		return this.getJsonString();
	}
}
