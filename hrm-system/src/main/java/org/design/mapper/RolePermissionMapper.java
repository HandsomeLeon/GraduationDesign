package org.design.mapper;

import org.apache.ibatis.annotations.Param;
import org.design.model.RolePermission;

import java.util.List;

public interface RolePermissionMapper {

    List<Integer> findPermissionByRoleId(@Param("roleId") Integer roleId);

    Integer delete(@Param("roleId") String roleId);

    void save(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}
