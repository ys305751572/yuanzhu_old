package com.bluemobi.sys.page;

import java.util.Collection;

public class PageContainer<E, K, V> implements Page<E> {

	/** 记录的总数 */
	private long totalnum = 0;

	/** 记录的总页数 */
	private int totalpage = 0;
	
	private int current = 0;

	/** 记录集合 */
	private Collection<E> info;

	public PageContainer(long totalnum, int totalpage, Collection<E> info,int current) {
		this.totalnum = totalnum;
		this.totalpage = totalpage;
		this.info = info;
		this.current = current;
	}

	@Override
	public long getTotal() {
		return totalnum;
	}

	@Override
	public int getPageCount() {
		return totalpage;
	}

	@Override
	public Collection<E> getRows() {
		return info;
	}

	@Override
	public int getCurrent() {
		
		return current;
	}
}
