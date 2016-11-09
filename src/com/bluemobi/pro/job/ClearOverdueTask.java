package com.bluemobi.pro.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluemobi.constant.Constant;
import com.bluemobi.utils.AbstractJob;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;

/**
 * 定期清理过期任务
 * @author yesong
 *
 */
public class ClearOverdueTask extends AbstractJob{

	String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
	String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
	String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
	String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	@Override
	public void execute() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>定期清理过期任务<<<<<<<<<<<<<<<<<<<<<<");
		_clear();
	}

	private void _clear() {
		List<Map<String,Object>> taskList = taskList();
		System.out.println("size:" + taskList.size());
		try {
			for (Map<String, Object> map : taskList) {
				
				String sql1 = returnCoinToUser(map);
				System.out.println("returnCoinToUser==========" + sql1);
				String sql2 = updateTaskStatus(map);
				System.out.println("updateTaskStatus============" + sql2);
				ps.addBatch(sql1);
				ps.addBatch(sql2);
			}
			ps.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	
	// 关闭链接
	private void close() {
		LocalDriverManager.closeConnection(conn, ps, rs);
	}

	private String updateTaskStatus(Map<String, Object> map) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE task SET status = 4 WHERE id =")
			.append(map.get("id"));
		return buffer.toString();
	}

	private String returnCoinToUser(Map<String, Object> map) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE stuuser SET coin =(")
			.append(map.get("taskCoin"))
			.append("+ coin) WHERE id =")
			.append(map.get("releaseUserId"));
		return buffer.toString();
		
	}

	public List<Map<String,Object>> taskList() {
		
		List<Map<String,Object>> taskList = null;
		try{
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "SELECT * FROM task t WHERE t.`task_end_time` < NOW() and t.`status` = 0";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			taskList = new ArrayList<Map<String,Object>>();
			while(rs.next()) {
				Map<String,Object> rsMap =  new HashMap<String,Object>();
				int id = rs.getInt("id");
				int releaseUserId = rs.getInt("release_user_id");
				int taskCoin = rs.getInt("task_coin");
				
				rsMap.put("id", id);
				rsMap.put("releaseUserId",releaseUserId);
				rsMap.put("taskCoin", taskCoin);
				
				taskList.add(rsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return taskList;
	}
}
