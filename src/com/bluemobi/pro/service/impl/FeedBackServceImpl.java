package com.bluemobi.pro.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluemobi.pro.pojo.Feedback;
import com.bluemobi.sys.service.BaseService;

/**
 * 
 * <p>Title: FeedBackServceImpl.java</p> 
 * <p>Description: 反馈service</p> 
 * @author 叶松 
 * @date 2014年12月15日 上午11:04:46
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Service
public class FeedBackServceImpl extends BaseService{

	private static final String PREFIX =  Feedback.class.getName();
	
	public int saveFeedBack( Map<String, Object> params ) throws Exception {
		params.put("backTime",System.currentTimeMillis());
		return this.getBaseDao().save(PREFIX + ".saveFeedBack", params);
	}
}
