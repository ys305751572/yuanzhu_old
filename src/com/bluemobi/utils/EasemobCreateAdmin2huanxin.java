package com.bluemobi.utils;

import net.sf.json.JSONObject;
import com.bluemobi.constant.TokenCache;
import com.bluemobi.pro.pojo.EasemobToken;
import com.bluemobi.pro.pojo.EasemobTokenForm;

/**
 * 环信 util
 * @author gaolei
 */
public class EasemobCreateAdmin2huanxin {
	//true表示开发模式，token取EasemobUtil.TOKEN。false表示发布模式,token从TokenCache获取
	//正式部署的时候这里要改成false
	public static boolean DEBUG_MODE = true;
	//org_name
	public static String ORG_NAME = "aidyoufu66";
	//app_name
	public static String APP_NAME = "aidyoufu";
	//debug临时token，调用getAccessToken()获取YWMtCOwi9HqOEeSWet2LRRBrbAAAAUtC0DxPSE_66Tqv-laJh1irzBr0Rm4FFFF
	public static String TOKEN = "YWMt6OlRknwoEeS5aGcejSi84gAAAUtNVPIZD0URmJeEmabjfK94RaVuxXgmrto";
	//登录用户名密码 aidyoufu66/Aidyoufu66
	
	//获取token
	public static String GET_TOKEN_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/token";
	//创建用户
	private static String CREATE_USER_URL = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/users";
	
	public static void main(String[] args) {
		// 获取token
//		getAccessToken();
		
//		EasemobUser eu = new EasemobUser();
//		eu.setUsername("admin");
//		eu.setPassword("123456");
//		eu.setNickname("admin");
//		JSONObject json = JSONObject.fromObject(eu);
//		createUser(json.toString());
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
		if (!EasemobCreateAdmin2huanxin.DEBUG_MODE) {
			TokenCache.check();
		}
		System.out.println("===== createUser ===== url:"+CREATE_USER_URL);
		System.out.println("===== createUser ===== body:"+body);
		String resp = HttpRequest.httpsRequest(CREATE_USER_URL, body, "POST","application/json;charset=utf-8");
		System.out.println("===== createUser ===== resp:"+resp);
		return resp;
	}
}
