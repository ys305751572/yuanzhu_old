package com.bluemobi.pro.pojo;


/**
 * @author gaolei
 */
public class EasemobTokenForm {
	
	/*******客户********/
	private String grant_type = "client_credentials";
	private String client_id = "YXA6O3yOALKUEeSkpCeNeyW4RQ";
	private String client_secret = "YXA6uNb12C0vEx6UHSev2qk4wnl_B0M";
	
	/********测试**
	private String grant_type = "client_credentials";
	private String client_id = "YXA6QqhGMGlyEeSygasZHZ103A";
	private String client_secret = "YXA6ra-kcFcWkwkTD8oX_H8lQ3EcK3U";
	*******/
	
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grantType) {
		grant_type = grantType;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String clientId) {
		client_id = clientId;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String clientSecret) {
		client_secret = clientSecret;
	}
	
}
