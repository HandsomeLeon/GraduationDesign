package org.design.mapper;

import org.apache.ibatis.annotations.Param;
import org.design.model.Employee;

import java.util.List;

public interface EmployeeMapper {

    Integer save(Employee employee);

    Integer delete(Integer id);

    Integer update(Employee employee);

    Employee get(Integer id);

    List<Employee> findAll();

    List<Employee> findExample(Employee employee);

    Employee findByUsername(String username);

    Integer updateRole(Integer id, @Param("roleId") Integer roleId);

    List<Employee> findManagerByRoleId(@Param("roleId") Integer roleId);

    Integer updatePassword(String id, String password);
}
