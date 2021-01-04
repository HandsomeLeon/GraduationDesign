package org.design.service;

import org.design.model.Employee;

public interface EmployeeService extends BaseService<Employee, Integer> {

    Employee findByUsername(String username);
}
