package com.bluemobi.sys.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluemobi.sys.dao.SysuserDao;
import com.bluemobi.sys.dao.base.DaoSupport;
import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;

/**
 * Sysuser数据访问接口
 * @author mew
 *
 */
@Component
public class SysuserDaoImpl implements SysuserDao {
	@Autowired	
	private DaoSupport dao;
 	/**
    *查询Sysuser一条记录,根据其他条件查询
    */
	@Override
	 public Sysuser get(Map<String, Object> params) {
	
		return dao.get(PREFIX + ".get", params);
	}
	
    /**
    *查询Sysuser一条记录，根据Id查询
    */
	@Override
	public <K, V> Map<K, V> findOne(Map<K, V> params) {
	
		return dao.get(PREFIX + ".findOne", params);
	}

	/**
    *查询Sysuser多条记录，返回一个list集合
    */
	@Override
	public <T, K, V> List<T> find(Map<K, V> params) {
	
		return dao.find(PREFIX + ".find", params);
	}

	/**
    *添加Sysuser
    */
	@Override
	public int insert(Sysuser sysuser) {
	
		return dao.insert(PREFIX + ".insert", sysuser);
	}

	/**
    *更新Sysuser
    */
	@Override
	public int update(Map<String, Object> params) {
	
		return dao.update(PREFIX + ".update", params);
	}

	/**
    *删除加Sysuser
    */
	@Override
	public int delete(Map<String, Object> params) {
	
		return dao.delete(PREFIX + ".delete", params);
	}

	/**
    *分页查询Sysuser
    */	
	@Override
	public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize) {
	
		return dao.page(PREFIX + ".page", params, current, pagesize);
	}

	/**
	 * 用户登录
	 */
	@Override
	public Sysuser getLogin(Map<String, Object> params) {
		
		return dao.get(PREFIX + ".getLogin", params);
		
		
	}
}


