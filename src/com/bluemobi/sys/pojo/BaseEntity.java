package com.bluemobi.sys.pojo;

import java.io.Serializable;

/**
 * 
 * <p>Title: BaseEntity.java</p> 
 * <p>Description:POJO基类</p> 
 * @author yesong 
 * @date 2014-11-5 下午02:55:03
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 8231037027139813504L;
	/****************** 基础公共属性 ********************/
	/** 创建时间 */
	private long createTime; 
	/** 创建人 */
	private String createUser;
	/** 更新时间 */
	private long updateTime;
	/** 更新人 */
	private String updateUser;
	/** 选择顺序 */
	private int indexNum;
	
	/********************** 翻页属性  暂留********************/
	/** 当前页 */
	private int pageNo = 1;
	
	/** 每页显示的数目 */
	private int pageSize = 10;
	
	/** 记录总条数 */
	private int totalNum;
	
	/** 记录总页数  */
	private int totalPage;

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}
}
