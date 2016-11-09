package com.bluemobi.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PageUtiles {
	
	private final static int CURRENT = 1;
	private final static int PAGESIZE = 10;
	
	
	/**
	 * 获取分页信息
	 * @param params
	 * @return
	 * @author liu_shi_lin
	 * @Date 2014-11-2 上午8:31:04
	 */
	public static int[] getPageInfos(Map<String,  Object> params){
		int[] info = new int[2];
		if(params.get("infoSize")==null||params.get("infoSize").toString().trim().length()==0){
			info[0] = 1000;
		}else{
			info[0] = (Integer)params.get("infoSize");
		}
		if(params.get("pageSize")==null||params.get("pageSize").toString().trim().length()==0){
			info[1] = 0;
		}else{
			info[1] = (Integer)params.get("pageSize");
		}
		return info;		
	}
	
	public static int parseCurrent(String current){
    	if (StringUtils.isNotEmpty(current)) {
    		return Integer.parseInt(current);
		}else {
			return CURRENT;
		}
    }
	public static int parsePageSize(String pageSize){
		if (StringUtils.isNotEmpty(pageSize)) {
			return Integer.parseInt(pageSize);
		}else {
			return PAGESIZE;
		}
	}

}
