package org.design.service;

import org.design.model.Employee;

import java.util.List;

public interface EmployeeService extends BaseService<Employee, Integer> {

    Employee findByUsername(String username);

    void updateRole(Integer id, Integer roleId);

    List<Employee> findManagerByRoleId(Integer roleId);

    void updatePassword(String id, String newPassword);

    Employee findManager(Integer managerId);
}
