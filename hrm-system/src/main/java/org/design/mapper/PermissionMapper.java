package org.design.mapper;

import org.design.model.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> findPermissionListByUsername(String username);
}
