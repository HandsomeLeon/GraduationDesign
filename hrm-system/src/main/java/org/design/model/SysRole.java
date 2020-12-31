package org.design.model;

import java.util.List;

public class SysRole {
    private Integer id;

    private String name;

    private String available;
    
    private List<SysPermission> permissionList;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available == null ? null : available.trim();
    }

	public List<SysPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<SysPermission> permissionList) {
		this.permissionList = permissionList;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", available=" + available + ", permissionList="
				+ permissionList + "]";
	}
	
}