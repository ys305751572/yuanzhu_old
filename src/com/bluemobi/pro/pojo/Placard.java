package com.bluemobi.pro.pojo;

import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * placard表
 * 
 * @author TuTu
 * 
 */
public class Placard extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -985564349788266758L;
    private java.lang.Integer id; // 公告编号
    private java.lang.String content; // 公告内容
    private String title;
    private java.lang.Integer stuUserId; // 移动端用户编号
    private java.lang.String time; // 发布时间
    private java.lang.Integer provinceId; // 省份编号
    private java.lang.Integer cityId; // 城市编号
    private java.lang.Integer schoolId; // 学校编号
    private int collegeId; // 学院编号
    private int type; // 类别 1对外 2对内
    private java.lang.String picture; // 公告图片URL
    private String linkUrl; // 链接地址
    
    public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	private Integer userType;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	//非数据库字段 
    private String sortColumn;//排序规则 叶松 2014-11-17
    
    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getContent() {
        return content;
    }

    public void setContent(java.lang.String content) {
        this.content = content;
    }

    public java.lang.Integer getStuUserId() {
        return stuUserId;
    }

    public void setStuUserId(java.lang.Integer stuUserId) {
        this.stuUserId = stuUserId;
    }

    public java.lang.String getTime() {
        return time;
    }

    public void setTime(java.lang.String time) {
        this.time = time;
    }

    public java.lang.Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(java.lang.Integer provinceId) {
        this.provinceId = provinceId;
    }

    public java.lang.Integer getCityId() {
        return cityId;
    }

    public void setCityId(java.lang.Integer cityId) {
        this.cityId = cityId;
    }

    public java.lang.Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(java.lang.Integer schoolId) {
        this.schoolId = schoolId;
    }

    public java.lang.String getPicture() {
        return picture;
    }

    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
}
