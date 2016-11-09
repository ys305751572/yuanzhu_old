package com.bluemobi.sys.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bluemobi.pro.pojo.IpValidata;
import com.bluemobi.pro.pojo.ValidataCode;

/**
 * 
 * <p>Title: DataCache.java</p> 
 * <p>Description: 缓存-初始化时需要缓存的数据</p> 
 * @author yesong 
 * @date 2014-11-5 下午05:57:33
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class DataCache extends HashMap<String, Object>{

	private static final long serialVersionUID = 71114158734241501L;

	private static DataCache instance = new DataCache();
	
	public static DataCache getInstance(){
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public void putMap(String moduleCode,Object value) {
		Map<String,Object> map = (Map<String, Object>) this.get(moduleCode);
		if( map != null) {
			map.put(moduleCode, value);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void putIps(IpValidata ipValidata) {
		Object map = this.get("ips");
		if( map != null) {
			((Map<String,Object>)map).put(ipValidata.getIp(), ipValidata);
		}
	}
	
	/**
	 * 
	 * @param moduleCode 父类Key
	 * @param code 子类Key
	 * @return
	 */
	public Object getObjectByCode(String moduleCode,String code) {
		Object obj = null;
		@SuppressWarnings("unchecked")
		Map<String,Object> map = (Map<String, Object>) this.get(moduleCode);
		if(map != null && !map.isEmpty()){
			obj = map.get(code);
		}
		return obj;
	}
	
	public Object getMapByModuleCode(String moduleCode) {
		if( StringUtils.isNotBlank(moduleCode)) {
			return this.get(moduleCode);
		}
		return null;
	}
	
	public void removeModule(String module) {
		if(StringUtils.isNotBlank(module)) {
			this.remove(module);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeCode(String module ,String code) {
		if(StringUtils.isNotBlank(code) || StringUtils.isNotBlank(module)) {
			Map<String,Object> map = (Map<String,Object>) this.get("module");
			if(map != null) {
				map.remove(code);
			}
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public ValidataCode getValidataCodeByMobile(String mobile) {
		ValidataCode code = null;
		if(StringUtils.isNotBlank(mobile)) {
			code = (ValidataCode) this.getObjectByCode("code", mobile);
		} else {
			throw new IllegalArgumentException("手机号不能为空...");
		}
		if( code == null) {
			code = new ValidataCode(mobile);
			Map<String,Object> map = (Map<String, Object>) this.get("code");
			if(map == null) {
				map = new HashMap<String,Object>();
			}
			map.put(mobile, code);
			DataCache.getInstance().put("code", map);
		}
		return code;
	}
	
	public void putCode(ValidataCode code) {
		
		HashMap<String,Object> _map = new HashMap<String,Object>();
		_map.put(code.getMobile(), code);
		DataCache.getInstance().put("code", _map);
	}
}
