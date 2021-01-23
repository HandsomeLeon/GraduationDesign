package org.design.mapper;

import org.design.model.Role;

import java.util.List;

public interface RoleMapper {

    Role get(Integer id);

    List<Role> findAll();
}
