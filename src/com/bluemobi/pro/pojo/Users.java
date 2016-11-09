package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * users表
 * 
 * @author TuTu
 * 
 */
public class Users extends BaseEntity {

    private static final long serialVersionUID = 6981634414090717523L;
    private java.lang.Integer id; // 移动端用户编号
    private java.lang.String userName; // 登录名
    private java.lang.String realName; // 用户姓名
    private java.lang.String password; // 密码
    private java.lang.String mobile; // 手机
    private java.lang.String userType; // 用户类型，1为普通管理员，2为系统管理员 3.任务管理员 4.咨询管理员 5.活动管理员
    
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

	public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getRealName() {
        return realName;
    }

    public void setRealName(java.lang.String realName) {
        this.realName = realName;
    }

    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public java.lang.String getMobile() {
        return mobile;
    }

    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }

    public java.lang.String getUserType() {
        return userType;
    }

    public void setUserType(java.lang.String userType) {
        this.userType = userType;
    }
}