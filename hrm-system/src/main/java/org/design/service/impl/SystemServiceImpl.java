package org.design.service.impl;

import org.design.mapper.PermissionMapper;
import org.design.mapper.RolePermissionMapper;
import org.design.model.MenuTree;
import org.design.model.Permission;
import org.design.service.SystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionListByUsername(String username) {

        return permissionMapper.findByUsername(username);
    }

    @Override
    public List<Permission> findPermissionList() {
        return null;
    }

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

}
