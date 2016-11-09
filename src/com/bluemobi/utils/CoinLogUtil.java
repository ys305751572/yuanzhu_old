package com.bluemobi.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Coinlog;

public class CoinLogUtil {

	/**
	 * 新增爱友币记录
	 * @param stuUserId
	 * @param coin
	 */
	public static void insertCoinLog(Coinlog coinlog) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver", Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url", Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "insert into coinlog (stuUserId,coin,createTime,status1,type,content) VALUES (?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, coinlog.getStuUserId());
			ps.setInt(2, coinlog.getCoin());
			ps.setLong(3, coinlog.getCreateTime());
			ps.setInt(4, 1);// 设置已成功
			ps.setInt(5, 1);
			ps.setString(6, coinlog.getContent());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
}
