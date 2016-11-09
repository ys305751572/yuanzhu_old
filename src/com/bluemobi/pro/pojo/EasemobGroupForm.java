package com.bluemobi.pro.pojo;


/**
 * 环信接口创建群组from对象
 * @author gaolei
 */
public class EasemobGroupForm {
	private String id;
	private String groupname;
	//群描述给默认值
	private String desc = "youzhugroupdesc";
	//是否公开给默认值
	private boolean __public = false;
	//默认200
	private int maxusers ;
	//加入公开群是否需要批准给默认值true
	private boolean approval = true;
	private String owner;
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean is__public() {
		return __public;
	}
	public void set__public(boolean public1) {
		__public = public1;
	}
	public int getMaxusers() {
		return maxusers;
	}
	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
