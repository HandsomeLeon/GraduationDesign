package org.design.service.impl;

import org.activiti.engine.task.Task;
import org.design.mapper.PermissionMapper;
import org.design.mapper.RoleMapper;
import org.design.mapper.RolePermissionMapper;
import org.design.model.MenuTree;
import org.design.model.Permission;
import org.design.model.Role;
import org.design.service.SystemService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> findPermissionListByUsername(String username) {

        return permissionMapper.findByUsername(username);
    }

    /*@Override
    public List<Permission> findPermissionListByRoleId(Integer roleId) {
        Role role = roleMapper.get(roleId);
        if (permissionList == null) {
            throw new ServiceException("获取数据失败");
        }
        return permissionList;
    }*/

    @Override
    public List<MenuTree> findMenuTreeList(String username) {

        List<MenuTree> menuTreeList = permissionMapper.findOwnedMenu(username);
        if (menuTreeList != null && menuTreeList.size() > 0) {
            for (MenuTree menuTree : menuTreeList) {
                menuTree.setChildren(permissionMapper.findSubPermission(username, String.valueOf(menuTree.getId())));
            }
        }
        return menuTreeList;
    }

    @Override
    public Role findRole(Integer id) {

        Role role = roleMapper.get(id);
        if (role == null) {
            throw new ServiceException("获取数据失败");
        }
        return role;
    }

    @Override
    public List<Role> findRoleList() {
        List<Role> roleList = roleMapper.findAll();
        if (roleList == null) {
            throw new ServiceException("获取数据失败");
        }
        return roleList;
    }

    @Override
    public List<MenuTree> findAllPermission() {
        List<MenuTree> menuTreeList = permissionMapper.findAllPermission();
        if (menuTreeList == null) {
            throw new ServiceException("获取数据失败");
        }
        return menuTreeList;
    }

    @Override
    public List<Integer> findPermissionByRoleId(Integer roleId) {
        List<Integer> permissionIdList = rolePermissionMapper.findPermissionByRoleId(roleId);
        if (permissionIdList == null) {
            throw new ServiceException("获取数据失败");
        }
        return permissionIdList;
    }

    @Override
    public void updateRole(String roleId, List<String> permissionIds) {
        Integer result = rolePermissionMapper.delete(roleId);
        if (result <= 0) {
            throw new ServiceException("操作失败");
        }
        for (String permissionId : permissionIds) {
            rolePermissionMapper.save(roleId, permissionId);
        }
    }

}
