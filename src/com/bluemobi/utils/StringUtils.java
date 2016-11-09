package com.bluemobi.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.bluemobi.pro.controller.UsersController;

/**
 * 
 * <p>Title: StringUtils.java</p> 
 * <p>Description:</p> 
 * @author yesong 
 * @date 2014-12-4 下午02:34:57
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */   
public class StringUtils {

	 private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>()
     {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
     };

	
	/**
	 * yeosng
	 * 查找
	 * @param str 完整字符串
	 * @param s 需要查找的字符串
	 * @param pos 第几次出现
	 * @return
	 */
	public static int findIndexFromPos(String str,String s,int pos) {
		if(org.apache.commons.lang3.StringUtils.isBlank(str)){
			throw new IllegalAccessError("str不能为空");
		}
		if(str.length() < s.length() || str.length() < pos){
			throw new IllegalAccessError("str长度不能小于s或者s长度不能小于pos");
		}
		Matcher matcher = Pattern.compile(s).matcher(str);
		int _index = 0;
		while(matcher.find()){
			_index++;
			if(_index == pos){
				break;
			}
		}
		return matcher.start();
	}
	
	@SuppressWarnings("unused")
	public static String friendly_time(long sdate) {
		Date time = new Date(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	public static void main(String[] args) {
		/**
		String str = "http://10.58.174.229:8080/youzhu/upload/temp/1417670604484_284737.jpg";
		String s = "/";
		int pos = 4;
		int _index = findIndexFromPos(str, s, pos);
		
		 String webpath = (UsersController.class.getResource("/").getPath().substring(0,
                 UsersController.class.getResource("/").getPath().indexOf("WEB-INF")));
		String path =str.substring(_index);
		File file = new File(webpath + path);
		try {
			BufferedImage image = ImageIO.read(file);
			System.out.println(image.getWidth() + ":" + image.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(file.getName());
		**/
		
		//System.out.println(friendly_time(1417597812302L));
	}
}
