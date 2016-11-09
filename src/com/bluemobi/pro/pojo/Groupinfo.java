package com.bluemobi.pro.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * groupinfo表
 * @author mew
 *
 */
public class Groupinfo implements Serializable {
	private String id; // 群编号
	private java.lang.String name; // 群名称
	private java.lang.Integer scaleId; // 群规模编号
	private java.lang.Integer groupType; // 话题类型（群类型）
	private java.lang.Integer provinceId; // 群省份编号
	private java.lang.Integer cityId; // 群城市编号
	private java.lang.Integer schoolId; // 群学校
	private java.lang.Integer stuUserId; // 群主编号
	private java.lang.String notice; // 群公告
	private java.lang.String message; // 群寄语
	private java.lang.String head; // 群头像URL
	private java.lang.String status; // 认证状态，0=未审核，1=审核通过，2=审核未通过
	private java.lang.Integer coin; // 爱心币
	private String qrCode;
	
	private long createTime;
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.Integer getScaleId() {
		return scaleId;
	}
	public void setScaleId(java.lang.Integer scaleId) {
		this.scaleId = scaleId;
	}
	public java.lang.Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(java.lang.Integer groupType) {
		this.groupType = groupType;
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
	public java.lang.Integer getStuUserId() {
		return stuUserId;
	}
	public void setStuUserId(java.lang.Integer stuUserId) {
		this.stuUserId = stuUserId;
	}
	public java.lang.String getMessage() {
		return message;
	}
	public void setMessage(java.lang.String message) {
		this.message = message;
	}
	public java.lang.String getHead() {
		return head;
	}
	public void setHead(java.lang.String head) {
		this.head = head;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getNotice() {
		return notice;
	}
	public void setNotice(java.lang.String notice) {
		this.notice = notice;
	}
	public java.lang.Integer getCoin() {
		return coin;
	}
	public void setCoin(java.lang.Integer coin) {
		this.coin = coin;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}