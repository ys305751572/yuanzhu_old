package com.bluemobi.pro.pojo;

import java.util.List;

/**
 * 角色POJO
 * @author yesong
 *
 */
public class Role {

	private int id;
	private String roleName;
	
	private List<Module> moduleList; // 角色拥有的模块权限

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}
	
	
	
}
