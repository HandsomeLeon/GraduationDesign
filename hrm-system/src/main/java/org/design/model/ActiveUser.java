package org.design.model;

import java.io.Serializable;
import java.util.List;

/**
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 * 
 * @author Thinkpad
 * 
 */
public class ActiveUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String userid;//用户id（主键）
	private String usercode;// 用户账号
	private String username;// 用户名称
	 private Long managerId;

	private List<Permission> menus;// 菜单
	private List<Permission> permissions;// 权限
	private List<MenuTree> menuTree;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<Permission> getMenus() {
		return menus;
	}

	public void setMenus(List<Permission> menus) {
		this.menus = menus;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<MenuTree> getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(List<MenuTree> menuTree) {
		this.menuTree = menuTree;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	@Override
	public String toString() {
		return "ActiveUser [id=" + id + ", userid=" + userid + ", usercode=" + usercode + ", username=" + username
				+ ", managerId=" + managerId + ", menus=" + menus + ", permissions=" + permissions + ", menuTree="
				+ menuTree + "]";
	}

}
