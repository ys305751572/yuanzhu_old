package com.bluemobi.pro.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bluemobi.constant.Constant;
import com.bluemobi.utils.AbstractJob;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;

/**
 * 
 * <p>Title: ClearSignJob.java</p>
 * <p>Description: 每月清除签到记录</p> 
 * @author yesong 
 * @date 2015年5月15日 下午2:05:10
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class ClearSignJob extends AbstractJob{

	private static List<Integer> crankRewordList = new ArrayList<Integer>();
	private static List<Integer> grankRewordList = new ArrayList<Integer>();
	
	static{
		crankRewordList.add(20000);
		crankRewordList.add(15000);
		crankRewordList.add(10000);
		crankRewordList.add(4000);
		
		grankRewordList.add(10000);
		grankRewordList.add(8000);
		grankRewordList.add(6000);
		grankRewordList.add(2000);
	}
	
	@Override
	public void execute() {
		System.out.println("=====================发放排行榜奖励======================");
		reword();
		System.out.println("=====================清除每月记录======================");
		clear();
	}

	private void reword() {
		try {
			List<Entity> grankList = findGrankList();
			List<Entity> crankList = findCrankList();
			
			rewordGroup(grankList);
			rewordUser(crankList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rewordUser(List<Entity> crankList) {
		if(crankList != null && crankList.size() > 0 && crankList.size() >= 4) {
			int size = crankList.size();
			try {
				for (int i = 0; i < size; i++) {
					int index = (i >= 3 ? 3 : i);
					int coin = crankRewordList.get(index);
					Entity entity = crankList.get(i);
					entity.setCoin(coin);
					updateUserCoin(entity);
				} 
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void rewordGroup(List<Entity> grankList) {
		if(grankList != null && grankList.size() > 0 && grankList.size() >= 4) {
			int size = grankList.size();
			try {
				for (int i = 0; i < size; i++) {
					int index = (i >= 3 ? 3 : i);
					int coin = grankRewordList.get(index);
					Entity entity = grankList.get(i);
					entity.setCoin(coin);
					updateGroupCoin(entity);
				} 
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<Entity> findGrankList() {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Entity> list = new ArrayList<Entity>();
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "SELECT g.id ,(a.groupnum * 100 + g.coin) sortValue FROM groupinfo g JOIN (SELECT groupId,count(groupId) groupnum " 
					+ "FROM groupmember group by groupId ORDER BY groupnum DESC limit 100) AS a ON  g.id = a.groupId "
					+ "ORDER BY sortValue DESC LIMIT 20";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Entity entity = new Entity();
				entity.setGid(rs.getString("id"));
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
		return list;
	}
	
	public List<Entity> findCrankList() {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Entity> list = new ArrayList<Entity>();
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "SELECT s.id,COUNT(gm.monthsign) counts FROM  groupmember gm JOIN  stuuser s  ON  gm.`stuUserId` = s.`id`" 
			+ "GROUP BY gm.stuUserId ORDER BY counts DESC LIMIT 20";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Entity entity = new Entity();
				entity.setId(rs.getInt("id"));
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
		return list;
	}
	
	public void updateGroupCoin(Entity entity) {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "UPDATE groupinfo SET coin = (coin +" + entity.getCoin() + ") WHERE id = " + entity.getGid();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
	
	public void updateUserCoin(Entity entity) {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "UPDATE stuuser SET coin = (coin +" + entity.getCoin() + ") WHERE id = " + entity.getId();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
	
	private void clear() {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = LocalDriverManager.getConnection(driverName, url, username, password);
			String sql = "UPDATE groupmember g SET g.monthsign = 0";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
}

class Entity{
	private int id;
	private String gid;
	private int coin;
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
}
