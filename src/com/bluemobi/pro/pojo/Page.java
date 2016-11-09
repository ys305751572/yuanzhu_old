package com.bluemobi.pro.pojo;


/**
 * 
 * 接口返回json	page对象
 * @author gaolei
 *
 */
public class Page {
	private int currentPage;
	private int totalNum;
	private int totalPage;
	
	public Page(){}
	
	public Page(int currentPage,int totalNum,int totalPage){
		this.currentPage = currentPage;
		this.totalNum = totalNum;
		this.totalPage = totalPage;
	}
	
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}