package com.bluemobi.pro.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 *@Title: 任务POJO
 *@Description:
 *@Author:Administrator
 *@Since:2015年6月11日
 *@Version:1.1.0
 */
public class Task {

	private int task_id;                    // 任务ID
	private int release_user_id;       // 发布人ID
	private String release_user_name;  // 发布者姓名
	private String release_user_mobile;// 发起人手机
	private String release_user_avater;// 发起人头像
	private int task_type_id;          // 任务类型ID
	private String task_type_name;     // 任务类型名称
	private Integer task_sex;          // 接单性别需求
	private Double task_lat; 		   // 纬度 
	private Double task_lon;           // 经度
	private String task_address;       // 任务地址
	private String task_end_time;      // 活动截止时间
	private Integer task_coin ;        // 任务奖励
	private String task_title;         // 任务标题
	private String task_desc;          // 任务详情
	private Integer accept_id;         // 接单人id
	private String accept_name;        // 接单人名字
	private String accept_avater;      // 接单人头像
	private Integer accept_status;     // 接单状态  0未接单 1已接单
	private String accept_mobile;      // 接收人手机号

	private Integer status; 
	
	private int is_friend; 			   // 是否为好友 0:不是好友 1:好友
	private String distance;           // 用户当前位置与任务距离
	
	private int task_reward; // 奖励金额
	private String groupId;
	private Integer isJoin = 0; // 是否加入该群 0:未加入 1:已加入
	
	public Integer getIsJoin() {
		return isJoin;
	}
	public void setIsJoin(Integer isJoin) {
		this.isJoin = isJoin;
	}
	
	private List<TaskRep> rep_list = new ArrayList<TaskRep>();

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getTask_reward() {
		return task_reward;
	}

	public void setTask_reward(int task_reward) {
		this.task_reward = task_reward;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAccept_mobile() {
		return accept_mobile;
	}

	public void setAccept_mobile(String accept_mobile) {
		this.accept_mobile = accept_mobile;
	}

	public String getAccept_avater() {
		return accept_avater;
	}

	public void setAccept_avater(String accept_avater) {
		this.accept_avater = accept_avater;
	}

	public String getRelease_user_avater() {
		return release_user_avater;
	}

	public void setRelease_user_avater(String release_user_avater) {
		this.release_user_avater = release_user_avater;
	}

	public String getRelease_user_mobile() {
		return release_user_mobile;
	}

	public void setRelease_user_mobile(String release_user_mobile) {
		this.release_user_mobile = release_user_mobile;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getRelease_user_id() {
		return release_user_id;
	}

	public void setRelease_user_id(int release_user_id) {
		this.release_user_id = release_user_id;
	}

	public String getRelease_user_name() {
		return release_user_name;
	}

	public void setRelease_user_name(String release_user_name) {
		this.release_user_name = release_user_name;
	}

	public int getTask_type_id() {
		return task_type_id;
	}

	public void setTask_type_id(int task_type_id) {
		this.task_type_id = task_type_id;
	}

	public String getTask_type_name() {
		return task_type_name;
	}

	public void setTask_type_name(String task_type_name) {
		this.task_type_name = task_type_name;
	}

	public Integer getTask_sex() {
		return task_sex;
	}

	public void setTask_sex(Integer task_sex) {
		this.task_sex = task_sex;
	}

	public Double getTask_lat() {
		return task_lat;
	}

	public void setTask_lat(Double task_lat) {
		this.task_lat = task_lat;
	}

	public Double getTask_lon() {
		return task_lon;
	}

	public void setTask_lon(Double task_lon) {
		this.task_lon = task_lon;
	}

	public String getTask_address() {
		return task_address;
	}

	public void setTask_address(String task_address) {
		this.task_address = task_address;
	}

	public String getTask_end_time() {
		return task_end_time;
	}

	public void setTask_end_time(String task_end_time) {
		this.task_end_time = task_end_time;
	}

	public Integer getTask_coin() {
		return task_coin;
	}

	public void setTask_coin(Integer task_coin) {
		this.task_coin = task_coin;
	}

	public String getTask_title() {
		return task_title;
	}

	public void setTask_title(String task_title) {
		this.task_title = task_title;
	}

	public String getTask_desc() {
		return task_desc;
	}

	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}

	public Integer getAccept_id() {
		return accept_id;
	}

	public void setAccept_id(Integer accept_id) {
		this.accept_id = accept_id;
	}

	public String getAccept_name() {
		return accept_name;
	}

	public void setAccept_name(String accept_name) {
		this.accept_name = accept_name;
	}

	public Integer getAccept_status() {
		return this.accept_status;
	}

	public void setAccept_status(Integer accept_status) {
		this.accept_status = accept_status;
	}

	public int getIs_friend() {
		return is_friend;
	}

	public void setIs_friend(int is_friend) {
		this.is_friend = is_friend;
	}

	public List<TaskRep> getRep_list() {
		return rep_list;
	}

	public void setRep_list(List<TaskRep> rep_list) {
		this.rep_list = rep_list;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}
