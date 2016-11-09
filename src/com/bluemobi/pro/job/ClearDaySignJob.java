package com.bluemobi.pro.job;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bluemobi.constant.Constant;
import com.bluemobi.utils.AbstractJob;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;

/**
 * 
 * <p>Title: ClearDaySignJob.java</p> 
 * <p>Description: 清除每天签到</p> 
 * @author yesong 
 * @date 2015年5月15日 下午2:57:47
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class ClearDaySignJob extends AbstractJob {

	@Override
	public void execute() {
		System.out.println("=====================清除每天记录======================");
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "UPDATE groupmember g SET g.daysign = 0";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
}
