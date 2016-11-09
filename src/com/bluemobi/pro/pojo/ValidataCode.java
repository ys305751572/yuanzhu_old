package com.bluemobi.pro.pojo;

import java.io.IOException;
import java.util.Random;
import org.apache.commons.lang.StringUtils;

import com.bluemobi.utils.JavaSmsApi;

/**
 * 
 * @Title: 验证码pojo
 * @Description:
 * @Author:yesong
 * @Since:2015年6月15日
 * @Version:1.1.0
 */
public class ValidataCode {

	/** 可发送最多次数 */
	private final static Integer TIMES = 5;

	/** 手机号 */
	private String mobile;

	/** 手机发送次数 */
	private Integer mobileSendCount = TIMES;

	/** 验证码 */
	private String volidateCode;

	public ValidataCode(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	// 发送验证码
	public Boolean sendSms(String mobile) {
		if (this.validate()) {
			try {
				generateCode();
				System.out.println("generateCode is :" + this.volidateCode);
				JavaSmsApi.sendSms(this.volidateCode, mobile);
				reduceOnceTime();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Boolean.valueOf(true);
		} else {
			return Boolean.valueOf(false);
		}
	}

	/**
	 * 
	 * @Description:发送完之后减少IP/手机可发送次数
	 */
	private void reduceOnceTime() {
		this.mobileSendCount -= 1;
	}

	// 验证验证码
	public Boolean validateCode(String code) {
		System.out.println("验证码验证===========================:输入:" + code + ":==================发送:" + this.volidateCode);
		if (StringUtils.isNotBlank(code) && code.equals(this.volidateCode)) {
			return Boolean.valueOf(true);
		} else {
			return Boolean.valueOf(false);
		}
	}

	public void addMobileTimes() {
		this.mobileSendCount += 1;
	}

	public Integer getMobileSendCount() {
		return mobileSendCount;
	}

	public void setMobileSendCount(Integer mobileSendCount) {
		this.mobileSendCount = mobileSendCount;
	}

	public Boolean validate() {
		if (canSendByMobile()) {
			return true;
		}
		return false;
	}

	/**
	 * 生成二维码
	 * 
	 * @Description:
	 */
	public void generateCode() {
		Random rd = new Random();
		int randonNum = 0;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < 6; i++) {
			if (i == 0) {
				randonNum = rd.nextInt(9) + 1;
			} else {
				randonNum = rd.nextInt(9);
			}
			sb.append(randonNum);
		}
		this.volidateCode = sb.toString();
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			ValidataCode vc =  new ValidataCode("13476107753");
			vc.generateCode();
			System.out.println(vc.getVolidateCode());
		}
	}
	
	// 检测手机号是否达到最大发送次数
	public Boolean canSendByMobile() {
		return getMobileSendCount() <= 0 ? false : true;
	}

	public String getVolidateCode() {
		return volidateCode;
	}

	public void setVolidateCode(String volidateCode) {
		this.volidateCode = volidateCode;
	}
}