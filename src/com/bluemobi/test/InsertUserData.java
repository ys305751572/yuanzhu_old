package com.bluemobi.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUserData {

	public static Connection getConnection(String url,String driver,String username,String password) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void batchInsert() {
		String sql = "insert  into `stuuser`(`nickname`,`name`,`password`,`mobile`,`email`,`birthday`,`provinceId`,`cityId`,`sex`,`startYear`,`schoolId`,`collegeId`,`specialtyId`,`status`,`recUserId`,`head`,`word`,`coverPic`,`contentPic`,`coin`,`labelId`,`isOnline`,`perfectInfo`) VALUES" ;
		String values = "";
		for (int i = 0; i < 2000; i++) {
			values += "('恶魔','张学友','e10adc3949ba59abbe56e057f20f883e','18771569034','123@qq.com','2015-1-14',18,257,'0','1995',11336,1133603,113360304,'2',0,'http://10.58.168.91:8888/youzhu/upload/temp/1421415264847_164350.jpg','','http://10.58.168.91:8888/youzhu/upload/temp/1421213883556_215341.png','http://10.58.168.91:8888/youzhu/upload/temp/1421213883572_774892.png',600,4,'1',1),";
		}
		Connection conn = getConnection("jdbc:mysql://127.0.0.1:3306/youzhu?characterEncoding=utf-8&autoReconnect=true","com.mysql.jdbc.Driver","root","root");
		String _sql = (sql + values).substring(0, (sql + values).length() - 1) ;
		
		long startTime = System.currentTimeMillis();
		System.out.println("sql:" + _sql);
		try {
			PreparedStatement ps = conn.prepareStatement(_sql);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long endTime  = System.currentTimeMillis();
		System.out.println("总共耗时:" + ((endTime - startTime)/1000) + "秒");
	}
	
	public static void main(String[] args) {
		batchInsert();
	}
}
