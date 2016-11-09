package com.bluemobi.sys.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.job.ClearDaySignJob;
import com.bluemobi.pro.job.ClearOverdueTask;
import com.bluemobi.pro.job.ClearSignJob;
import com.bluemobi.pro.job.TestJob;
import com.bluemobi.pro.pojo.IpValidata;
import com.bluemobi.pro.pojo.ValidataCode;
import com.bluemobi.sys.bean.DataCache;
import com.bluemobi.test.GenerateQrCodeTest;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;
import com.bluemobi.utils.QuartzManager;


/**
 * 
 * <p>Title: InitServlet.java</p> 
 * <p>Description: 初始化加载数据到DataCache</p> 
 * @author yesong 
 * @date 2014-11-6 上午09:51:50
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
public class InitServlet extends HttpServlet{

	private static final long serialVersionUID = 2110499739711866579L;
	protected final Log logger = LogFactory.getLog(getClass());
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger.info("初始化开始.................");
		//TODO 加载需要初始化加载的数据 加入代码时请标明作者、时间、描述
		
		//========================================================
		// 初始化学校数据
		//========================================================
		initScshool();
		//========================================================
		//启动作业任务
		//========================================================
		startWork();
//		testWork();
		// 初始化没有二维码的数据
		GenerateQrCodeWithOut();
		//初始化验证码缓存
//		initValidateCode() ;
		// 初始化IP访问验证
//		initValidataIp();
		logger.info("初始化结束.................");
	}
	
	private void initValidataIp() {
		Map<String,IpValidata> ipMap = new HashMap<String, IpValidata>();
		DataCache.getInstance().put("ips",ipMap);
	}

	/**
	 * @Description:初始化验证码缓存
	 */
	private void initValidateCode() {
		Map<String,ValidataCode> codeMap = new HashMap<String,ValidataCode>();
		DataCache.getInstance().put("code", codeMap);
	}

	//启动作业任务
	//yesong
	//2015-5-15
	private void startWork() {
		System.out.println("启动初始化任务");
		// 开启 每月15号清除签到数据
		String jobName = "clearSign";
		String groupName = "sign1";
		String triggerName = "sign1";
		//Constant.CLEAR_SIGN_CRON,
		QuartzManager.addJob(jobName, groupName, triggerName, Constant.CLEAR_SIGN_CRON, new ClearSignJob());
		
		// 开启 每天清除签到数据
		jobName = "dayClearSign";
		groupName = "sign";
		triggerName = "sign";
		//Constant.DAY_CLEAR_SIGN_CRON
		QuartzManager.addJob(jobName, groupName, triggerName, Constant.DAY_CLEAR_SIGN_CRON,new ClearDaySignJob());
		
		// 开启 每天清除过期的任务
		jobName = "clearTask";
		groupName = "sign2";
		triggerName = "sign2";
		//Constant.CLEAR_SIGN_CRON,
		QuartzManager.addJob(jobName, groupName, triggerName, Constant.DAY_CLEAR_SIGN_CRON, new ClearOverdueTask());
	}
	
	// 初始化学校数据
	// yesong
	// 2015-5-15
	private void initScshool() {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver", Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url", Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		Connection conn = LocalDriverManager.getConnection(driverName, url, username, password);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select s.id, s.proName, s.pid, s.provinceId, s.cityId FROM scs s";
		Map<String,Object> allScs = new HashMap<String, Object>();
		Map<String,Object> scs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(); 
			while(rs.next()) {
				Long id = rs.getLong("id");
				int provinceId = rs.getInt("provinceId");
				int cityId = rs.getInt("cityId");
				String proName = rs.getString("proName");
				
				String key = "" + provinceId + cityId + id;
				scs = new HashMap<String, Object>();
				scs.put("proName", proName);
				allScs.put(key, scs);
			}
			
		DataCache.getInstance().put("scs", allScs);
		logger.info("allScs.size():" + allScs.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
	}
	
	private void GenerateQrCodeWithOut() {
		GenerateQrCodeTest.findAllWithoutQrCode();
	}
}
