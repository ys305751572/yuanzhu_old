package com.bluemobi.pro.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bluemobi.constant.Constant;
import com.bluemobi.utils.AbstractJob;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;

/**
 * 
 * <p>Title: QueryGroupSortJob.java</p> 
 * <p>Description: 群排序JOB</p> 
 * @author yesong 
 * @date 2015年5月14日 上午10:48:28
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Deprecated
public class QueryGroupSortJob extends AbstractJob{

	@Override
	public void execute() {
		String driverName = PropertiesUtils.getPropertiesValues("jdbc.driver", Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url", Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		
		Connection conn = LocalDriverManager.getConnection(driverName, url, username, password);
		//select *,(groupnum * 100 + coin) values1 from groupinfo g join (SELECT groupId,count(groupId) groupnum FROM groupmember group by groupId order by groupnum desc limit 100) as  a on g.id = a.groupId
		String sql = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps, rs);
		}
	}
}
