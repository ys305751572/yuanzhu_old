package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * <p>Title: Years.java</p> 
 * <p>Description: 入学年份</p> 
 * @author yesong 
 * @date 2014-11-17 上午10:53:30
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class Years extends BaseEntity{

	private static final long serialVersionUID = 8763020464813643016L;

	private int id;
	private String year;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
