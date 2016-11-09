package com.bluemobi.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import com.bluemobi.constant.Constant;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;
import com.bluemobi.utils.QrCodeUtil;

public class GenerateQrCodeTest {

	static String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
	static String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
	static String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
	static String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
	
	List<Map<String,Object>> list = null;
	
	public static void updateQrCode() {
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
	
	public static void findAllWithoutQrCode() {
		System.out.println("===============findAllWithoutQrCode() 初始化二维码================");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = 
					"SELECT id ,0 AS 'type','stuuser' AS 'table_name' FROM stuuser WHERE qrCode = NULL OR qrCode = '' " + 
					"UNION ALL " +
					"SELECT id ,2 AS 'type','activities' AS 'table_name' FROM activities WHERE qrCode = NULL OR qrCode =''" +
					"UNION ALL " + 
					"SELECT id ,1 AS 'type' ,'groupinfo' AS 'table_name' FROM groupinfo WHERE qrCode = NULL OR qrCode = ''";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				int type = rs.getInt("type");
				String tableName = rs.getString("table_name");
				QrCodeUtil.generateQrCode(type, id, tableName,false,0);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
	
	public static void main(String[] args) {
		findAllWithoutQrCode();
	}
}
