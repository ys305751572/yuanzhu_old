package com.bluemobi.pro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Province;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.service.BaseService;

/**
 * 
 * <p>Title: T_CordServiceImpl.java</p> 
 * <p>Description: t_code数据service: 例如省，城市，年纪此类的定值可以一起存放在t_code表中，也就是码表</p> 
 * @author yesong 
 * @date 2014-11-17 上午09:57:10
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@SuppressWarnings("rawtypes")
@Service
public class TCordServiceImpl extends BaseService{
	
	public static final String PREFIX = Province.class.getName();
	
	/**
	 * 获取省份
	 * @return
	 * @throws Exception
	 */
	public List getProvince() throws Exception {
		return this.getBaseDao().getList(PREFIX + ".queryProvince");
	}
	
	/**
	 * 获取城市
	 * @return
	 * @throws Exception
	 */
	public List getCity(Map<String, Object> map) throws Exception {
		return this.getBaseDao().getList(PREFIX + ".queryCity",map);
		
	}
	
	/**
	 * 获取学校学院专业列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page getScs(Map<String, Object> map) throws Exception {
		String pageNum = (String) map.get("pageNum");
		String pageSize = (String) map.get("pageSize");
		Object provinceId = map.get("provinceId");
		if(provinceId != null){
			int _provinceId = Integer.parseInt(provinceId.toString());
			if(_provinceId == 1 || _provinceId == 2 || _provinceId == 3 || _provinceId == 4){
				map.put("cityId", provinceId);
			}
		}
		return this.getBaseDao().page(PREFIX + ".queryScs", map,Integer.parseInt(pageNum) ,Integer.parseInt(pageSize));
	}
	
	/**
	 * 获取年份
	 * @return
	 * @throws Exception
	 */
	public List getYear() throws Exception {
		return this.getBaseDao().getList(PREFIX + ".queryYear");
	}
	
	/**
	 * 获取兴趣
	 * @return
	 * @throws Exception
	 */
	public List getInterest() throws Exception {
		return this.getBaseDao().getList(PREFIX + ".queryInterest");
	}
	
	/**
	 * 获取标签
	 * @return
	 * @throws Exception
	 */
	public List getLabel() throws Exception {
		return this.getBaseDao().getList(PREFIX + ".queryLabel");
	}
}

