package com.bluemobi.sys.dao.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.bluemobi.constant.Constant;
import com.bluemobi.pro.pojo.Groupinfo;
import com.bluemobi.pro.service.impl.IUpdateUtf8mb4;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.page.PageContainer;
import com.bluemobi.utils.LocalDriverManager;
import com.bluemobi.utils.PropertiesUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * 
 * <p>Title: BaseDao.java</p> 
 * <p>Description: dao基类</p> 
 * @author yesong 
 * @date 2014-11-5 下午05:13:30
 * @version V1.0 
 * ------------------------------------
 * 历史版本
 *
 */
@Repository
public class BaseDao extends SqlSessionDaoSupport{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	public int save(String key, Object obj) throws Exception {
		return this.getSqlSession().insert(key, obj);
	}
	
	/**
	 * 设定本次链接字符集未utf8mb4
	 * @param key
	 * @param obj
	 * @return
	 * @throws Exception
	 * @Description:
	 */
	public int saveWithUft8mb4(String key, Object obj) throws Exception {
		String driver =	PropertiesUtils.getPropertiesValues("jdbc.driver",Constant.JDBC_PROP);
		String url = PropertiesUtils.getPropertiesValues("jdbc.url",Constant.JDBC_PROP);
		String username = PropertiesUtils.getPropertiesValues("jdbc.username", Constant.JDBC_PROP);
		String password = PropertiesUtils.getPropertiesValues("jdbc.password", Constant.JDBC_PROP);
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = LocalDriverManager.getConnection(driver, url, username, password);
			
			conn.setAutoCommit(true);
			//通过查询运行设置字符集的命令
	        //conn.prepareStatement("set names utf8mb4").executeQuery();
	        PreparedStatement cmd = conn.prepareStatement("insert into groupinfo (id,stuUserId,message) values(?,?,?)");
	        cmd.setString(1, ((Groupinfo)obj).getId());
	        cmd.setInt(2, 558738);
	        cmd.setString(3, ((Groupinfo)obj).getMessage());
	        cmd.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LocalDriverManager.closeConnection(conn, ps);
		}
		 
		LocalDriverManager.getConnection(driver, url, username, password);
		
		
		return 0;
	}
	
	public int update(String key, Object obj) throws Exception{
		return this.getSqlSession().update(key, obj);
	}
	
	public int delete(String key, Serializable obj) throws Exception {
		return this.getSqlSession().delete(key, obj);
	}
	
	public int delete(String key, Object obj) throws Exception {
		return this.getSqlSession().delete(key, obj);
	}
	
	@SuppressWarnings("unchecked") 
	public <T> T get(String key, Object param) throws Exception {
		return (T) this.getSqlSession().selectOne(key, param);
	}
	

	public <T> T getObject(String key, Object param) throws Exception{
		List<T> objList = this.getSqlSession().selectList(key, param);
		if(objList==null || objList.size()==0)
			return null;
		return objList.get(0);
		
	}
	
	public <T> List<T> getList(String key) throws Exception {
		return this.getSqlSession().selectList(key);
	}
	
	public <T> List<T> getList(String key, Object param) throws Exception {
		return this.getSqlSession().selectList(key, param);
	}
	
	@SuppressWarnings("unchecked")
	public <E, K, V> Page<E> page(String pageStatement, Map<K, V> parameter, int current, int pagesize) {
		PageBounds pageBounds = new PageBounds(current, pagesize);
		PageList<E> result = (PageList<E>) this.getSqlSession().selectList(pageStatement, parameter, pageBounds);
		Paginator paginator = result.getPaginator();
		return new PageContainer<E, K, V>(paginator.getTotalCount(), paginator.getTotalPages(), result,current);
	}
}
