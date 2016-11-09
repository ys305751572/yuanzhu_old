package com.bluemobi.pro.pojo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bluemobi.constant.Constant;
import com.bluemobi.sys.pojo.BaseEntity;

/**
 * 
 * <p>Title: Activities.java</p> 
 * <p>Description: 活动POJO</p> 
 * @author yesong
 * @date 2014-11-13 下午03:36:58
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class Activities extends BaseEntity{

	private static final long serialVersionUID = 1256337859799555593L;

	private int id;// id;
	private String content ;//活动内容
	private long createTime; //发布时间
	private int provinceId;//省份编号
	private int cityId;//城市编号
	private int schoolId;//学校ID
	
	private int typeId;// 类型编号
	private int stuUserId;//活动发起人编号
	private long startTime;//活动开始时间
	private long endTime; //活动结束时间
	
	private int permission;
	
	private String picture;//活动图片
	private int maxCount;//限制人数
	private int status ;//审核状态，0=未审核，1=审核通过，2=审核未通过
	private String location ;//活动地址
	private int pwidth;//图片宽度
	
	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getPwidth() {
		return pwidth;
	}

	public void setPwidth(int pwidth) {
		this.pwidth = pwidth;
	}
	
	public int getPheight() {
		return pheight;
	}

	public void setPheight(int pheight) {
		this.pheight = pheight;
	}


	private int pheight;//图片高度
	
	//非数据库字段
	private String typeName;//类型名称
	private List<ActComment> commentList = null;//评论集合
	
	private int isFull; //参与人数是否已满 0:已满,1:未满
	
	private int isFavoriteByMe; //我是否收藏 0:已收藏 1:未收藏
	
	private int favoriteCount ;//收藏数
	private int commentCount ;//评论数
	
	private int joinNum;// 参加的人数
	
	private String head ;//活动发起人头像
	private String name;// 活动发起人姓名
	
	private String activityTime;
	
	private double lon; // 经度
	private double lat; // 纬度
	
	private String qrCode; // 二维码
	
	private String title; // 标题
	
	public Activities(){}
	
	public Activities(int provinceId,int cityId,int schoolId,String content,long createTime,int typeId,
			int stuUserId,long startTime,long endTime,String picture,int maxCount,String location,int pwidth,int pheight,double lon,double lat){
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.schoolId = schoolId;
		this.content = content;
		this.createTime = System.currentTimeMillis();
		this.typeId = typeId;
		this.stuUserId = stuUserId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.picture = picture;
		this.maxCount = maxCount;
		this.location = location;
		this.pwidth = pwidth;
		this.pheight = pheight;
		this.lon = lon;
		this.lat = lat;
	}
	
	public Activities(int provinceId,int cityId,int schoolId,String content,long createTime,int typeId,
			int stuUserId,long startTime,long endTime,String picture,int maxCount,String location,int pwidth,int pheight,double lon,double lat,String title){
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.schoolId = schoolId;
		this.content = content;
		this.createTime = System.currentTimeMillis();
		this.typeId = typeId;
		this.stuUserId = stuUserId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.picture = picture;
		this.maxCount = maxCount;
		this.location = location;
		this.pwidth = pwidth;
		this.pheight = pheight;
		this.lon = lon;
		this.lat = lat;
		this.title = title;
	}
	
	public Activities(int provinceId,int cityId,int schoolId,String content,long createTime,int typeId,
			int stuUserId,long startTime,long endTime,String picture,int maxCount,String location,int pwidth,int pheight,double lon,double lat,String title,int permission){
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.schoolId = schoolId;
		this.content = content;
		this.createTime = System.currentTimeMillis();
		this.typeId = typeId;
		this.stuUserId = stuUserId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.picture = picture;
		this.maxCount = maxCount;
		this.location = location;
		this.pwidth = pwidth;
		this.pheight = pheight;
		this.lon = lon;
		this.lat = lat;
		this.title = title;
		this.permission = permission;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getStuUserId() {
		return stuUserId;
	}
	public void setStuUserId(int stuUserId) {
		this.stuUserId = stuUserId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getPicture() {
		this.picture = StringUtils.isNotBlank(this.picture) ? this.picture : Constant.DEFUALT_ACT_HEAD;
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<ActComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<ActComment> commentList) {
		this.commentList = commentList;
	}

	public int getJoinNum() {
		return joinNum > maxCount ? 0 : 1;
	}

	public void setJoinNum(int joinNum) {
		this.joinNum = joinNum;
	}

	public int getIsFull() {
		this.isFull = this.joinNum > this.maxCount ? 0 : 1;
		return this.isFull;
	}

	public void setIsFull(int isFull) {
		this.isFull = isFull;
	}

	public int getIsFavoriteByMe() {
		return isFavoriteByMe > 0 ? 0 : 1;
	}

	public void setIsFavoriteByMe(int isFavoriteByMe) {
		this.isFavoriteByMe = isFavoriteByMe;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivityTime() {
		activityTime = this.getStartTime() + "~" + this.getEndTime();
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
}
