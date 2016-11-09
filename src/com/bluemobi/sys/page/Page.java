package com.bluemobi.sys.page;

import java.util.Collection;

/**
 * 
 * 分页器接口，根据page,pageSize,total用于页面上分页显示多项内容，计算页码和当前页的偏移量，方便页面分页使用.
 * 
 */
public interface Page<E> {
	/**
	 * 取总记录数
	 */
	public long getTotal();

	/**
	 * 取总页数.
	 */
	public int getPageCount();

	/**
	 * 得到该页的数据
	 */
	public Collection<E> getRows();

	
	public int getCurrent();
}
