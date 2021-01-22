package org.design.service.impl;

import org.design.mapper.PermissionMapper;
import org.design.mapper.RoleMapper;
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

}
