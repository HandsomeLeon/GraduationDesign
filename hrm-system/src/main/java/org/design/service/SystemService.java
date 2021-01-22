package org.design.service;

import org.design.model.MenuTree;
import org.design.model.Permission;
import org.design.model.Role;

import java.util.List;

public interface SystemService {

    List<Permission> findPermissionListByUsername(String username);

    //List<Permission> findPermissionListByRoleId(Integer roleId);

    /**
     * 查找一级菜单
     * @param username  用户名
     * @return
     */
    List<MenuTree> findMenuTreeList(String username);

    Role findRole(Integer id);
}
