package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * friend表
 * @author gaolei
 *
 */
public class Friend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5880768668564609062L;
	private java.lang.Integer stuUserId; // 移动端用户编号
	private java.lang.Integer friendId; // 好友编号
	public java.lang.Integer getStuUserId() {
		return stuUserId;
	}
	public void setStuUserId(java.lang.Integer stuUserId) {
		this.stuUserId = stuUserId;
	}
	public java.lang.Integer getFriendId() {
		return friendId;
	}
	public void setFriendId(java.lang.Integer friendId) {
		this.friendId = friendId;
	}

	
}