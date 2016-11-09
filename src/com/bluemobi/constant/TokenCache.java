package com.bluemobi.constant;

import java.util.HashMap;
import java.util.Map;

import com.bluemobi.pro.pojo.EasemobToken;
import com.bluemobi.utils.EasemobUtil;

/**
 * TOEKN缓存
 *
 */
public class TokenCache {
	public final static String ACCESS_TOKEN = "access_token";
	private static Map<String,EasemobToken> map = new HashMap<String,EasemobToken>();
	/**
	 * 保存accesstoken
	 * @param accessToken
	 */
	public static void putAccessToken(EasemobToken accessToken){
		map.put(ACCESS_TOKEN,accessToken);
	}
	/**
	 * 获取accesstoken
	 */
	public static EasemobToken getAccessToken(){
		return map.get(ACCESS_TOKEN);
	}
	
	/**
	 * 判断accessToken是否失效
	 */
	public static void check(){
		try {
			EasemobToken token = getAccessToken();
			if(token != null){
				long endTime = token.getEnd_time();
				long newTime = System.currentTimeMillis();
				if(endTime <= newTime){
					EasemobUtil.getAccessToken();
				}
			}
			else {
				EasemobUtil.getAccessToken();
			}
		} catch (Exception e) {
			EasemobUtil.getAccessToken();
		}
	}
}
