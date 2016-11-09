package com.bluemobi.pro.pojo;

import java.sql.Timestamp;
import java.util.List;


/**
 * 帖子评论
 * @author yesong
 *
 */
public class PostComment {

	private Integer id;
	private Integer fromUserId;
	private String fromUserHead;
	private String fromUserName;
	
	private Integer toUserId;
	private String toUserHead;
	private String toUserName;
	
	private String content;
	private Timestamp createDate;
	
	private List<PostComment> list;
	
	public List<PostComment> getList() {
		return list;
	}

	public void setList(List<PostComment> list) {
		this.list = list;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public String getToUserHead() {
		return toUserHead;
	}

	public void setToUserHead(String toUserHead) {
		this.toUserHead = toUserHead;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserHead() {
		return fromUserHead;
	}

	public void setFromUserHead(String fromUserHead) {
		this.fromUserHead = fromUserHead;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
