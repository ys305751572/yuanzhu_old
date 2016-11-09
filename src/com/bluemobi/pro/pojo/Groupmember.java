package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * 群成员表
 * @author gaolei
 *
 */
public class Groupmember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4914751721075578324L;
	private String groupId; // 群编号
	private java.lang.Integer stuUserId; // 用户编号
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public java.lang.Integer getStuUserId() {
		return stuUserId;
	}
	public void setStuUserId(java.lang.Integer stuUserId) {
		this.stuUserId = stuUserId;
	}
	
	
}