package org.design.service.impl;

import org.design.mapper.PermissionMapper;
import org.design.mapper.RolePermissionMapper;
import org.design.model.Employee;
import org.design.model.Permission;
import org.design.service.EmployeeService;
import org.design.service.SystemService;

import javax.annotation.Resource;
import java.util.List;

public class SystemServiceImpl implements SystemService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionListByUsername(String username) {

        List<Permission> permissionList = permissionMapper.findPermissionListByUsername(username);
        return permissionList;
    }

}
