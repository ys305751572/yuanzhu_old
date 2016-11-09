package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * <p>Title: ActComment.java</p> 
 * <p>Description: 活动评论POJO</p> 
 * @author yesong 
 * @date 2014-11-13 下午03:54:24
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class ActComment extends BaseEntity{

	private static final long serialVersionUID = -674891682964997370L;

	private int id;//id
	private int activityId;// 活动id
	private int stuUserId;// 活动发起人ID
	private String content ;//评论类容
	private long createTime;//评论时间 
	
	public ActComment(){}
	
	public ActComment(int activityId,int stuUserId,String content){
		this.activityId = activityId;
		this.stuUserId = stuUserId;
		this.content = content;
		this.createTime = System.currentTimeMillis();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getStuUserId() {
		return stuUserId;
	}
	public void setStuUserId(int stuUserId) {
		this.stuUserId = stuUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
