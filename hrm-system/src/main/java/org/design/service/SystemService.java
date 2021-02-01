package org.design.service;

import org.activiti.engine.task.Task;
import org.design.model.MenuTree;
import org.design.model.Permission;
import org.design.model.Role;

import java.util.List;

public interface SystemService {

    List<Permission> findPermissionListByUsername(String username);

    /**
     * 查找一级菜单
     * @param username  用户名
     * @return
     */
    List<MenuTree> findMenuTreeList(String username);

    Role findRole(Integer id);

    List<Role> findRoleList();

    List<MenuTree> findAllPermission();

    List<Integer> findPermissionByRoleId(Integer roleId);

    void updateRole(String roleId, List<String> permissionIds);

}
