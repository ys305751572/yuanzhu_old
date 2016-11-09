package com.bluemobi.sys.dao;

import java.util.List;
import java.util.Map;

import com.bluemobi.sys.page.Page;
import com.bluemobi.sys.pojo.Sysuser;

/**
 * Sysuser数据访问接口
 * 
 */
public interface SysuserDao {

    public final static String PREFIX = SysuserDao.class.getName();

    /**
     * 查询Sysuser一条记录,根据其他条件查询
     */
    public Sysuser get(Map<String, Object> params);

    /**
     * 查询Sysuser一条记录,根据Id查询
     */
    public <K, V> Map<K, V> findOne(Map<K, V> params);

    /**
     * 查询Sysuser多条记录，返回一个list集合
     */
    public <T, K, V> List<T> find(Map<K, V> params);

    /**
     * 添加Sysuser
     */
    public int insert(Sysuser sysuser);

    /**
     * 更新Sysuser
     */
    public int update(Map<String, Object> params);

    /**
     * 删除加Sysuser
     */
    public int delete(Map<String, Object> params);

    /**
     * 分页查询Sysuser
     */
    public <E, K, V> Page<E> page(Map<K, V> params, int current, int pagesize);

    /**
     * 用户登录
     */
    public Sysuser getLogin(Map<String, Object> params);

}
