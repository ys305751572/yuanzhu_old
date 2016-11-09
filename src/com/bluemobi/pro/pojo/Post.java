package com.bluemobi.pro.pojo;

import java.sql.Timestamp;
import java.util.List;


/**
 * 帖子
 * @author yesong
 *
 */
public class Post {

	private Integer id;
	private String title;
	
	private String content;
	private Integer userId;
	private Integer postBarId;
	private String postBarName;
	
	private Timestamp createDate;
	
	private String userName;
	private String userHead;
	
	private Integer fcount;
	private Integer ccount;
	
	private String linkUrl; // 帖子外链地址
	
	private List<PostImage> picList;
	
	private Integer ispraise; // 是否点赞 0:未点赞 1:已点赞
	private Integer isList; // 是否上架  -1:未上架 1:已上架
	private Integer isTop; // 是否置顶 1:未置顶 2:已置顶
	
	private String groupId;
	private Integer isJoin = 0; // 是否加入该群 0:未加入 1:已加入
	
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getIsJoin() {
		return isJoin;
	}
	public void setIsJoin(Integer isJoin) {
		this.isJoin = isJoin;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public String getPostBarName() {
		return postBarName;
	}
	public void setPostBarName(String postBarName) {
		this.postBarName = postBarName;
	}
	public Integer getIsList() {
		return isList;
	}
	public void setIsList(Integer isList) {
		this.isList = isList;
	}
	public Integer getCcount() {
		return ccount;
	}
	public void setCcount(Integer ccount) {
		this.ccount = ccount;
	}
	public Integer getIspraise() {
		return ispraise;
	}
	public void setIspraise(Integer ispraise) {
		this.ispraise = ispraise;
	}
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getFcount() {
		return fcount;
	}
	public void setFcount(Integer fcount) {
		this.fcount = fcount;
	}
	public List<PostImage> getPicList() {
		return picList;
	}
	public void setPicList(List<PostImage> picList) {
		this.picList = picList;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPostBarId() {
		return postBarId;
	}
	public void setPostBarId(Integer postBarId) {
		this.postBarId = postBarId;
	}
}
