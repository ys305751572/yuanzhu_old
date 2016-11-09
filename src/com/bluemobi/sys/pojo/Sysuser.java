package com.bluemobi.sys.pojo;

import java.io.Serializable;

/**
 * 
 * sysuser表
 * @author mew
 *
 */
public class Sysuser implements Serializable {
	private java.lang.Integer id; // 
	private java.lang.String name; // 
	private java.lang.String password; // 
	private Integer userType;  // 用户类型，1为普通管理员，2为系统管理员 3.任务管理员 4.咨询管理员 5 活动管理员
	
	
	private Integer provinceId;
	private Integer cityId;
	private Integer schoolId;
	
	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
     * 获取属性
     *
     * @return id
     */
	public java.lang.Integer getId() {
		return id;
	}
	
	/**
	 * 设置属性
	 *
	 * @param id
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	/**
     * 获取属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取属性
     *
     * @return password
     */
	public java.lang.String getPassword() {
		return password;
	}
	
	/**
	 * 设置属性
	 *
	 * @param password
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Sysuser");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
		sb.append('}');
        return sb.toString();
    }
}