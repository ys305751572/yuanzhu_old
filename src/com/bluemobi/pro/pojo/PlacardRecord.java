package com.bluemobi.pro.pojo;

import java.util.Date;

/**
 * 咨询阅读列表
 * @author yesong
 *
 */
public class PlacardRecord {

	private Integer id;
	
	private StuUser user;
	
	private Date createDate;
	
	private String mobile;
	private String name;
	private String school;
	private String college;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StuUser getUser() {
		return user;
	}

	public void setUser(StuUser user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
