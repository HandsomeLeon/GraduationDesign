package org.design.mapper;

import org.design.model.Employee;

import java.util.List;

public interface EmployeeMapper {

    Integer save(Employee employee);

    Integer delete(Integer id);

    Integer update(Employee employee);

    Employee get(Integer id);

    List<Employee> findAll();

    List<Employee> findExample(Employee employee);
}
