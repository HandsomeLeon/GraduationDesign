package org.design.service;

import org.design.model.Permission;

import java.util.List;

public interface SystemService {

    List<Permission> findPermissionListByUsername(String username);

}
