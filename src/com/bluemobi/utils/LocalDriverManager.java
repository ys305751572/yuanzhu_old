package com.bluemobi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.StringUtils;

public class LocalDriverManager {
	
	public static Connection getConnection(String driverName,String url,String username,String password) {
		Connection conn = null;
		try {
			Class.forName(driverName);
			if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(url)) {
				conn = DriverManager.getConnection(url, username, password);
			}
			else{
				throw new IllegalArgumentException("param is exception..");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn ,PreparedStatement ps,ResultSet rs) {
		try {
			if(conn != null) {
				conn.close();
			}
			if( ps != null ) {
				ps.close();
			}
			if( rs != null ) {
				
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(Connection conn ,PreparedStatement ps) {
		closeConnection(conn, ps, null);
	}
}
