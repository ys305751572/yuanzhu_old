package com.bluemobi.pro.pojo;

/**
 * 模块POJO
 * @author yesong
 *
 */
public class Module {

	private int id;      // 模块ID
	private String name; // 模块名称
	private String url;  // 模块地址
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
