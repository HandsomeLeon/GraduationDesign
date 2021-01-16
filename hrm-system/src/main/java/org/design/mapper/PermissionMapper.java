package org.design.mapper;

import org.apache.ibatis.annotations.Param;
import org.design.model.MenuTree;
import org.design.model.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> findByUsername(String username);

    List<MenuTree> findOwnedMenu(String username);

    List<Permission> findSubPermission(@Param("username") String username, @Param("parentId") String parentId);

}
