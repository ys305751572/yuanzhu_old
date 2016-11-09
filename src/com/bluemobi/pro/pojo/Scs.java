package com.bluemobi.pro.pojo;

import java.io.Serializable;

/**
 * 
 * scs表
 * 
 * @author mew
 * 
 */
public class Scs implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2502973426865958759L;
    private java.lang.Integer id; // 编号
    private java.lang.String ProName; // 名称
    private java.lang.Integer pid; // 上级Id
    private java.lang.String level; // 级别，1=学校，2=学院，3=专业
    private java.lang.Integer provinceid; // 省份id
    private java.lang.Integer cityid; // 城市id

    /**
     * 获取编号属性
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 设置编号属性
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * 获取上级Id属性
     * 
     * @return pid
     */
    public java.lang.Integer getPid() {
        return pid;
    }

    /**
     * 设置上级Id属性
     * 
     * @param pid
     */
    public void setPid(java.lang.Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取级别，1=学校，2=学院，3=专业属性
     * 
     * @return level
     */
    public java.lang.String getLevel() {
        return level;
    }

    /**
     * 设置级别，1=学校，2=学院，3=专业属性
     * 
     * @param level
     */
    public void setLevel(java.lang.String level) {
        this.level = level;
    }

    /**
     * 获取省份id属性
     * 
     * @return provinceid
     */
    public java.lang.Integer getProvinceid() {
        return provinceid;
    }

    /**
     * 设置省份id属性
     * 
     * @param provinceid
     */
    public void setProvinceid(java.lang.Integer provinceid) {
        this.provinceid = provinceid;
    }

    /**
     * 获取城市id属性
     * 
     * @return cityid
     */
    public java.lang.Integer getCityid() {
        return cityid;
    }

    /**
     * 设置城市id属性
     * 
     * @param cityid
     */
    public void setCityid(java.lang.Integer cityid) {
        this.cityid = cityid;
    }

	public java.lang.String getProName() {
		return ProName;
	}

	public void setProName(java.lang.String proName) {
		ProName = proName;
	}

}