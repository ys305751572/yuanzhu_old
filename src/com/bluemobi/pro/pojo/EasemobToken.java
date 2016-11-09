package com.bluemobi.pro.pojo;

/**
 * 环信 token
 * @author gaoll
 */
public class EasemobToken{
	
	/**
	 * Token
	 */
	private String access_token;
	
	/**
	 * 超时时间，单位是毫秒
	 */
	private int expires_in;
	
	private String application;
	
	/**
	 * 开始时间
	 */
	private long beg_time;
	
	/**
	 * 结束时间
	 */
	private long end_time;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expiresIn) {
		expires_in = 24*60*60*1000;
		beg_time = System.currentTimeMillis();
		//有效期是一天，一天获取一次
		end_time = beg_time + 24*60*60*1000;
	}
	public long getBeg_time() {
		return beg_time;
	}
	public void setBeg_time(long begTime) {
		beg_time = begTime;
	}
	public long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(long endTime) {
		end_time = endTime;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
}
