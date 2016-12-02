package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 个性装扮
 * @author yesong
 *
 */
public class BackgroudImg implements Serializable{
	private static final long serialVersionUID = 3398810885104433842L;
	
	private int id;
	private String path;
	private long createTime;
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
