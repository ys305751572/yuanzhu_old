package com.bluemobi.pro.pojo;

public class IpValidata {

	private String ip;  // 访问IP地址
	private int count = 0; // 访问次数

	private int DEFAULT_COUNT = 5;
	
	public IpValidata(String ip) {
		this.ip = ip;
	}
	
	public void increase() {
		++this.count;
	}
	
	public Boolean validataIp() {
		if(this.count >= DEFAULT_COUNT) {
			return Boolean.valueOf(false);
		}
		return Boolean.valueOf(true);
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
