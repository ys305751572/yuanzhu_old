package com.bluemobi.pro.pojo;

import com.bluemobi.utils.YuanzhuUtils;

public class TaskExcel {

	private String username;
	private String mobile;
	private String alipay;
	private int money;
	private int status; // 奖励状态
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	
	public int getMoney() {
		return YuanzhuUtils.generateRewardMoney(this.money);
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getStatus() {
		return status == 1 ? "已奖励":"未奖励";
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
