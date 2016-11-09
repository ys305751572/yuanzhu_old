package com.bluemobi.pro.pojo;

public class ImageEntity {

	// 如果是放在映射表中 此字段表示外键ID
	private String superId;
	
	// 图片路径
	private String path;
	
	// 图片宽度
	private Double width;
	
	// 图片高度
	private Double height;

	public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
