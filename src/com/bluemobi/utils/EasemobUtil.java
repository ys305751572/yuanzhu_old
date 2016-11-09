package com.bluemobi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import com.bluemobi.constant.Constant;
import com.bluemobi.constant.TokenCache;
import com.bluemobi.pro.pojo.EasemobGroupForm;
import com.bluemobi.pro.pojo.EasemobGroupUpdate;
import com.bluemobi.pro.pojo.EasemobToken;
import com.bluemobi.pro.pojo.EasemobTokenForm;


/**
 * 环信 util
 * @author gaolei
 */
public class EasemobUtil {
	//true表示开发模式，token取EasemobUtil.TOKEN。false表示发布模式,token从TokenCache获取
	//正式部署的时候这里要改成false
	public static boolean DEBUG_MODE = false;
	//org_name
	public static String ORG_NAME = "";
	//app_name
	public static String APP_NAME = "";
	//debug临时token，调用getAccessToken()获取YWMtCOwi9HqOEeSWet2LRRBrbAAAAUtC0DxPSE_66Tqv-laJh1irzBr0Rm4FFFF
	//aiyoufu: YWMtS5x2Bs1MEeSCpdsJcNczuwAAAU1hFExLx-ACXd5C5_tN4hRrOOf7srCr4P4
	public static String TOKEN = "YWMtCYv31P-BEeSIuF_C6dJW8wAAAU6qHAcOp47nLwxdz3chYN6VTgXZlbsGs1I";
	//登录用户名密码 ryeladmin/ryeladmin
	
	static{
		ORG_NAME = PropertiesUtils.getPropertiesValues("ORG_NAME", Constant.HUANXIN_PROP);
		APP_NAME = PropertiesUtils.getPropertiesValues("APP_NAME", Constant.HUANXIN_PROP);
	}
	
	//获取token
	public static String GET_TOKEN_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/token";
	//创建用户
	private static String CREATE_USER_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/users";
	//添加好友
	private static String CREATE_FRIEND_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/users/%s/contacts/users/%s";
	//发送消息
	private static String SEND_MSG_2FRIEND_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/messages";
	//好友列表
	private static String MY_FRIEND_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/users/%s/contacts/users";
	//
	private static String GET_CHAR_MSGS_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatmessages";
	//创建群
	private static String CREATE_GROUP_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatgroups";
	//查看群详情
	private static String GET_GROUP_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatgroups/%s";
	//删除群组
	private static String DELETE_GROUP_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatgroups/%s";
	//获取一个用户参与的所有群组
	private static String JOINED_GROUPS = "https://a1.easemob.com/"+ORG_NAME+"/"+APP_NAME +"/users/%s/joined_chatgroups";
	//查看某个IM用户的好友信息
	private static String GET_FRIENDS_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/users/%s/contacts/users";
	//获取群组中的所有成员
	private static String GET_GROUP_MEMBERS_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatgroups/%s/users";
	//群添加成员
	private static String GROUP_ADD_MEMBER = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatgroups/%s/users/%s";
	//在群组中减少一人  
	private static String GROUP_REMOVE_MEMBER = "https://a1.easemob.com/"+ORG_NAME+"/"+APP_NAME +"/chatgroups/%s/users/%s";
	// 获取IM用户[单个]
	private static String GET_USER = "https://a1.easemob.com/"+ORG_NAME+"/"+APP_NAME +"/users/%s";
	
	//修改IM用户密码
	private static String CHAGE_PWD = "https://a1.easemob.com/"+ORG_NAME+"/"+APP_NAME +"/users/%s/password";
	
	//修改群信息
	public static String UPDATE_GROUP_INFO = "https://a1.easemob.com/"+ORG_NAME+"/"+APP_NAME + "/chatgroups";
	
	public static final String PREFIX = "easemob_";
	public static final String ADMIN = "admin";
	
	public static void main(String[] args) {
		// 获取token
//		getAccessToken();
		
//		LoadImUser l =  new LoadImUser();
//		List<?> list = l.loadFromTxt();
//		for (Object object : list) {
//			String[] strs = (String[]) object;
//			
//			EasemobGroupUpdate egu = new EasemobGroupUpdate();
//			egu.setMaxusers(600);
//			String url = UPDATE_GROUP_INFO + "/108229188179198536";
//			chanageGroupInfo(egu,url);
			
//			EasemobGroupForm easemobGroup = new EasemobGroupForm();
//			easemobGroup.setId(strs[0]);
//			easemobGroup.setGroupname(strs[1]);
//			easemobGroup.setDesc(strs[2]);
//			easemobGroup.set__public(true);
//			easemobGroup.setMaxusers(Integer.parseInt(strs[3]));
//			easemobGroup.setApproval(true);
//			easemobGroup.setOwner(strs[4]);
//			createGroup(easemobGroup);
			
//			groupAddMember(strs[0],strs[1]);
//			createFriend(strs[0],strs[1]);
//		}
		
		// 测试注册环信用户{'username':'easemob_111126','password':'1234566'}
//		EasemobUser eu = new EasemobUser();
//		eu.setUsername("easemob_557080");
//		eu.setPassword("fa5f6e5b27aef31b9b2b33b41e17bf10");
//		eu.setNickname("15261102356");
//		JSONObject json = JSONObject.fromObject(eu);
//		createUser(json.toString());
		
		// 修改群信息
		EasemobGroupUpdate groupForm = new EasemobGroupUpdate();
		groupForm.setId("124326185294365280"); // 116335821476332048
		groupForm.setMaxusers(600);
		
		modifyGroup(groupForm);
		 //测试发送好友请求
//		createFriend("easemob_556600", "easemob_556601");
		
//		deleteFriend("easemob_556600", "easemob_556601");
		
		// 测试发送文本信息
		//sendMsg2Friend("admin", "easemob_558743","活动审核通过","{'attr':'act','userId':'558734','actname':'活动11111','status':'1'}");
		
		// 测试查询好友列表
//		myFriend("easemob_556615");
		
		// 测试聊天记录
//		getChatMsgs();
		
		/**
			 * {
			    "groupname":"testrestgrp12", //群组名称, 此属性为必须的
			    "desc":"server create group", //群组描述, 此属性为必须的
			    "public":true, //是否是公开群, 此属性为必须的
			    "maxusers":300, //群组成员最大数(包括群主), 值为数值类型,默认值200,此属性为可选的
			    "approval":true, //加入公开群是否需要批准, 没有这个属性的话默认是true, 此属性为可选的
			    "owner":"jma1", //群组的管理员, 此属性为必须的
			    "members":["jma2","jma3"] //群组成员,此属性为可选的,但是如果加了此项,数组元素至少一个
				}
		 */
//		getGroup("142774232300460");
//		getGroup("1423840412916342");
		
		
//		getFriends("gaoleilei");
		
//		deleteGroup("1423840412916342");
		
//		getGroupMembers("1417745907901491");
		
//		groupAddMember("1417745907901491","easemob_556621");
		
//		groupRemoveMember("141895670053708","easemob_556619");
		
//		List list = joinedGroups("easemob_556806");
//		for (Object object : list) {
//			System.out.println(object.toString());
//		}
		
//		sendMsg2Friend("easemob_123456","easemob_111111");
		
//		getUser("easemob_556642");
		
//		modifyNickname("easemob_556626","呵呵呵");
		
		
	}
	
	
	
	/**
	 * 修改群信息
	 * @param userId
	 * @param body
	 * @return
	 */
	public static String chanageGroupInfo(EasemobGroupUpdate easemobGroupUpdate,String url){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		JSONObject json1 = JSONObject.fromObject(easemobGroupUpdate);
		String body = json1.toString();
		body = body.replaceAll("_public", "public");
		System.out.println("===== chanageGroupInfo ===== url:"+url);
		System.out.println("===== chanagePwd ===== body:"+ body);
		String resp = HttpRequest.httpsRequest(url, body, "PUT", "application/json;charset=utf-8");
		System.out.println("===== chanagePwd ===== resp:"+resp);
		return resp;
	}
	
	
	public static String chanagePwd(String userId,String body){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(CHAGE_PWD, userId);
		System.out.println("===== chanagePwd ===== body:"+ body);
		String resp = HttpRequest.httpsRequest(url, body, "PUT", "application/json;charset=utf-8");
		System.out.println("===== chanagePwd ===== resp:"+resp);
		return resp;
	}
	
	/**
	 * 获取环信token
	 * @author 高磊磊
	 */
	public static void getAccessToken(){
		EasemobTokenForm etf = new EasemobTokenForm();
		JSONObject json1 = JSONObject.fromObject(etf);
		String body = json1.toString();
		System.out.println("===== getAccessToken ===== url:"+GET_TOKEN_URL);
		System.out.println("===== getAccessToken ===== body:"+body);
		String resp = HttpRequest.httpsRequest(GET_TOKEN_URL, body, "POST","application/json;charset=utf-8");
		System.out.println("===== getAccessToken ===== resp:"+resp);
		EasemobToken easemobToken = (EasemobToken) JsonUtils.getObject4JsonString(resp, EasemobToken.class);
		TokenCache.putAccessToken(easemobToken);
	}
	
	public static String createUser(String body){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		System.out.println("===== createUser ===== url:"+CREATE_USER_URL);
		System.out.println("===== createUser ===== body:"+body);
		String resp = HttpRequest.httpsRequest(CREATE_USER_URL, body, "POST","application/json;charset=utf-8");
		System.out.println("===== createUser ===== resp:"+resp);
		return resp;
	}
	
	/**
	 *
	 * @param map 请求参数,参数如下：
	 *		ownerUsername 需要添加好友的用户key（必填）
	 *		friendUsername 待添加的好友key（必填）
	 * 是否调通：否
	 * @return json格式信息
	 * @author gaolei
	 */
	public static void createFriend(String ownerUsername,String friendUsername){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(CREATE_FRIEND_URL, ownerUsername,friendUsername);
		System.out.println("===== createFriend ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, "", "POST", "application/json;charset=utf-8");
		System.out.println("===== createFriend ===== resp:"+resp);
	}
	
	/**
	 * 删除好友
	 * 
	 * @author gaolei
	 */
	public static String deleteFriend(String ownerUsername,String friendUsername){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(CREATE_FRIEND_URL, ownerUsername,friendUsername);
		System.out.println("===== deleteFriend ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, "", "DELETE", "application/json;charset=utf-8");
		System.out.println("===== deleteFriend ===== resp:"+resp);
		return resp;
	}
	
	/*
	 * @author: jason
	 * @date: 2014-11-11 19:58:40
	 * @disc: 给某个用户发送信息
	 * */
	public static void sendMsg2Friend(String from, String to,String msg,String ext){
		if (!DEBUG_MODE) {
		      TokenCache.check();
	    }
	    String url = SEND_MSG_2FRIEND_URL;
	    String body = "{'target_type' : 'users','target' : ['" + 
	      to + "']," + 
	      "'msg' : { 'type' : 'txt', 'msg' : '" + msg + "'}," + 
	      "'from' : '" + from + "'," + "'ext' : " + ext + "}";
	    JSONObject json1 = JSONObject.fromObject(body);
	    System.out.println("body:" + body);
	    String resp = HttpRequest.httpsRequest(url, json1.toString(), "POST", "application/json;charset=utf-8");
	    System.out.println(resp);
	}
	
	/*
	 * @author: jason
	 * @date: 2014-11-11 19:58:40
	 * @disc: 给某个用户发送信息
	 * */
	public static void myFriend(String myName){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(MY_FRIEND_URL, myName);
		System.out.println("===== myFriend ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, "", "GET", "");
		System.out.println("===== myFriend ===== resp:"+resp);
	}
	
	/*
	 * @author: jason
	 * @date: 2014-11-11 19:58:40
	 * @disc: 给某个用户发送信息
	 * */
	public static void getChatMsgs(){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = GET_CHAR_MSGS_URL;
		System.out.println(url);
		String resp = HttpRequest.httpsRequest(url, "", "GET", "application/json;charset=utf-8");
		System.out.println(resp);
	}
	
	/**
	 * 创建群
	 * @author gaolei
	 */
	public static String createGroup(EasemobGroupForm eg){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		JSONObject jo = JSONObject.fromObject(eg);
		String body = jo.toString();
		body = body.replaceAll("__public", "public");
		System.out.println("===== createGroup ===== url:"+CREATE_GROUP_URL);
		System.out.println("===== createGroup ===== body:"+body);
		String resp = HttpRequest.httpsRequest(CREATE_GROUP_URL, body, "POST","application/json;charset=utf-8");
		System.out.println("===== createGroup ===== resp:"+resp);
		//提取groupid
		if (resp!=null) { 
			JSONObject json = JSONObject.fromObject(resp);
			JSONObject data = json.getJSONObject("data");
			String groupid = data.getString("groupid");
			return groupid;
		}
		return null;
	}
	
	/**
	 * 创建群
	 * @author gaolei
	 */
	public static String getGroup(String groupId){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String body = "";
		String url = String.format(GET_GROUP_URL, groupId);
		System.out.println("===== getGroup ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "GET","application/json;charset=utf-8");
		System.out.println("===== getGroup ===== resp:"+resp);
		System.out.println(resp);
		return resp;
	}
	
	/**
	 * 修改群信息	（是否调通:是）
	 * method必须是DELETE
	 * @author gaolei
	 */
	public static String modifyGroup(EasemobGroupUpdate eg){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(DELETE_GROUP_URL, eg.getId());
		
		JSONObject json1 = JSONObject.fromObject(eg);
		String body = json1.toString();
		System.out.println("===== modifyGroup ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "PUT","application/json;charset=utf-8");
		System.out.println("===== modifyGroup ===== resp:"+resp);
		return null;
	}
	
	/**
	 * 删除群组	（是否调通:是）
	 * method必须是DELETE
	 * @author gaolei
	 */
	public static String deleteGroup(String groupId){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(DELETE_GROUP_URL, groupId);
		String body = "";
		System.out.println("===== deleteGroup ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "DELETE","application/json;charset=utf-8");
		System.out.println("===== deleteGroup ===== resp:"+resp);
		//提取groupid
		if (resp!=null) {
			JSONObject json = JSONObject.fromObject(resp);
			JSONObject data = json.getJSONObject("data");
			String success = data.getString("success");
			return success;
		}
		return null;
	}
	
	/**
	 * 查询好友信息
	 * @return 好友list信息
	 * @author gaolei
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getFriends(String easemobUsername){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GET_FRIENDS_URL, easemobUsername);
		String body = "";
		System.out.println("===== getFriends ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "GET","application/json;charset=utf-8");
		System.out.println("===== getFriends ===== resp:"+resp);
		if (resp!=null) {
			//解析json
			Map<String, Object> map = JsonUtils.getMapFromJsonObjStr(resp);
			Object[] objs = (Object[]) JSONArray.toArray((JSONArray) map.get("data"));
			
			List<String> friends = new ArrayList<String>();
			for (int i = 0; i < objs.length; i++) {
				friends.add(objs[i].toString());
			}
			return friends;
		}
		return null;
	}
	
	/**
	 * 查询群成员列表
	 * @param map 请求参数,参数如下：
	 *		groupId 群id（必填）
	 * 是否调通：是
	 * @return List<String> 群成员username list
	 * @author gaolei
	 */
	public static List<String> getGroupMembers(String groupId){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GET_GROUP_MEMBERS_URL, groupId);
		String body = "";
		System.out.println("===== getGroupMembers ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "GET","application/json;charset=utf-8");
		System.out.println("===== getGroupMembers ===== resp:"+resp);
		List<String> strList = new ArrayList<String>();
		//解析jresp
		if (resp!=null) {
			JSONObject json = JSONObject.fromObject(resp);
			JSONArray data = json.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject j = data.getJSONObject(i);
				if (j.containsKey("member")) {
					strList.add((String) j.get("member"));
				}
			}
		}
		return strList;
	}
	
	/**
	 * 查询群成员列表（包括群主）
	 * @param map 请求参数,参数如下：
	 *		groupId 群id（必填）
	 * 是否调通：是
	 * @return List<String> 群成员username list
	 * @author gaolei
	 */
	public static List<String> getGroupMembersIncludeOwner(String groupId){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GET_GROUP_MEMBERS_URL, groupId);
		String body = "";
		System.out.println("===== getGroupMembersIncludeOwner ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "GET","application/json;charset=utf-8");
		System.out.println("===== getGroupMembersIncludeOwner ===== resp:"+resp);
		List<String> strList = new ArrayList<String>();
		//解析jresp
		if (resp!=null) {
			JSONObject json = JSONObject.fromObject(resp);
			JSONArray data = json.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject j = data.getJSONObject(i);
				if (j.containsKey("member")) {
					strList.add((String) j.get("member"));
				}
				if (j.containsKey("owner")) {
					strList.add((String) j.get("owner"));
				}
			}
		}
		return strList;
	}
	
	/**
	 * 群添加成员
	 * @param map 请求参数,参数如下：
	 *		groupId 群id
	 *		username 待添加的username
	 * 是否调通：是
	 * @return json格式信息
	 * @author gaolei
	 */
	public static String groupAddMember(String groupId,String username){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GROUP_ADD_MEMBER, groupId,username);
		String body = "";
		System.out.println("===== groupAddMember ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "POST","application/json;charset=utf-8");
		System.out.println("===== groupAddMember ===== resp:"+resp);
		System.out.println(resp);
		return resp;
	}
	
	/**
	 * 群组减少一个人
	 * 
	 * @author gaolei
	 */
	public static String groupRemoveMember(String groupId,String username){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GROUP_REMOVE_MEMBER, groupId,username);
		System.out.println("===== groupRemoveMember ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, "", "DELETE","application/json;charset=utf-8");
		System.out.println("===== groupRemoveMember ===== resp:"+resp);
		if (resp!=null) {
			JSONObject json = JSONObject.fromObject(resp);
			JSONObject data = json.getJSONObject("data");
			if (data.containsKey("result")) {
				String result = (Boolean) data.get("result") + "";
				return result;
			}
		}
		return resp;
	}
	
	/**
	 * 获取一个用户参与的所有群组
	 */
	public static List<String> joinedGroups(String username){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(JOINED_GROUPS,username);
		String body = "";
		System.out.println("===== joinedGroups ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, body, "GET","application/json;charset=utf-8");
		System.out.println("===== joinedGroups ===== resp:"+resp);
		
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotBlank(resp)) {
			JSONObject json = JSONObject.fromObject(resp);
			JSONArray data = json.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject j = data.getJSONObject(i);
				if (j.containsKey("groupid")) {
					list.add((String) j.get("groupid"));
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取IM用户[单个]
	 * 
	 * @author gaolei
	 */
	public static String getUser(String username){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GET_USER,username);
		System.out.println("===== getUser ===== url:"+url);
		String resp = HttpRequest.httpsRequest(url, "", "GET","application/json;charset=utf-8");
		System.out.println("===== getUser ===== resp:"+resp);
		return resp;
	}
	
	/**
	 * 修改用户昵称
	 * 
	 * @author gaolei
	 */
	public static String modifyNickname(String username,String nickname){
		if (!EasemobUtil.DEBUG_MODE) {
			TokenCache.check();
		}
		String url = String.format(GET_USER,username);
		String body ="{\"nickname\":\""+nickname+"\"}";
		System.out.println("===== modifyNickname ===== url:"+url);
		System.out.println("===== modifyNickname ===== body:"+body);
		String resp = HttpRequest.httpsRequest(url, body, "PUT","application/json;charset=utf-8");
		System.out.println("===== modifyNickname ===== resp:"+resp);
		return resp;
	}
}
