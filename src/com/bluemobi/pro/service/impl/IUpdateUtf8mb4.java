package com.bluemobi.pro.service.impl;

import org.apache.ibatis.annotations.Update;

/**
 * 
 *@Title: 执行SQL语句时，设定本次链接字符集未utf8mb4
 *@Description:yesong
 *@Author:Administrator
 *@Since:2015年6月12日
 *@Version:1.1.0
 */
public interface IUpdateUtf8mb4 {

	 @Update("set names utf8mb4")
	 public void setCharsetToUtf8mb4();
}
