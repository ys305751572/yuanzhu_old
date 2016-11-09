package com.bluemobi.pro.pojo;

/**
 * 
 *@Title: 任务图片POJO
 *@Description:
 *@Author:Administrator
 *@Since:2015年6月11日
 *@Version:1.1.0
 */
public class TaskRep {

	private int id;
	private String url;
	private Double width;
	private Double height;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
}
