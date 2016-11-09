package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * city表
 * 
 * @author mew
 * 
 */
public class City implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9199779530923685673L;
    private int id; // 主键
    private String name; // 名称
    private int provinceid; // 省份主键
    private String brevitycode; // 简码
    
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

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public String getBrevitycode() {
		return brevitycode;
	}

	public void setBrevitycode(String brevitycode) {
		this.brevitycode = brevitycode;
	}

}