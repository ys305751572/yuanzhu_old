package com.bluemobi.pro.controller.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.Result;

@Controller
@RequestMapping(value = "/api/test")
public class TestController {

	public static Map<String,Object> cache = new ConcurrentHashMap<>();
	
	static {
		System.out.println("初始化cache");
		if(cache == null) {
			cache = new ConcurrentHashMap<>();
		}
	}	
	
	@RequestMapping(value = "/user/get")
	@ResponseBody
	public Result findUsers() {
		
		Object value = cache.get("findUsers");
		if(value != null) {
			System.out.println("已从缓存取出");
			return Result.success();
		} 
		
		long startDate = System.currentTimeMillis();
		String sql = "SELECT s.`id`, s.`mobile`,s.`nickname`,c.`proName` FROM stuuser s LEFT JOIN scs c ON s.`schoolId` = c.`id` AND c.`level` = 1";
		Connection conn = LocalDriverManager.getConnection("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/youzhu?characterEncoding=utf-8&autoReconnect=true", "root", "root");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
				++count;
			}
			System.out.println("查询记录数：" + count);
			cache.put("findUsers", "true");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps, rs);
		}
		long endDate = System.currentTimeMillis();
		System.out.println("执行耗时:" + (endDate - startDate) / 1000.0 + "秒");
		return Result.success();
	}
}
