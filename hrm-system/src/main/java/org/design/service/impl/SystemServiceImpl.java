package org.design.service.impl;

import org.design.mapper.PermissionMapper;
import org.design.mapper.RolePermissionMapper;
import org.design.model.Permission;
import org.design.service.SystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> findPermissionListByUsername(String username) {

        return permissionMapper.findByUsername(username);
    }

    @Override
    public List<Permission> findPermissionList() {
        return null;
    }

}
