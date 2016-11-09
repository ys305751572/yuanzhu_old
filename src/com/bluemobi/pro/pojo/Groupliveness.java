package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 群活跃度
 * 
 * @author gaolei
 */
public class Groupliveness implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4914751721075578324L;
	private String groupId; // 群编号
	private java.lang.Integer stuUserId; // 用户编号
	private int speakNum;

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

	public int getSpeakNum() {
		return speakNum;
	}

	public void setSpeakNum(int speakNum) {
		this.speakNum = speakNum;
	}

}