package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 用户-个性装扮图 关联关系
 * @author yesongh
 *
 */
public class UserBgImg extends BaseEntity{

	private static final long serialVersionUID = -6037600709223880860L;
	
	private Integer id;
	private Integer userId;
	private String bgimgPath;
	private Long creatTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBgimgPath() {
		return bgimgPath;
	}
	public void setBgimgPath(String bgimgPath) {
		this.bgimgPath = bgimgPath;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Long creatTime) {
		this.creatTime = creatTime;
	}
}
