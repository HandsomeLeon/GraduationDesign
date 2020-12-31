package org.design.service.impl;

import org.design.mapper.EmployeeMapper;
import org.design.model.Employee;
import org.design.service.EmployeeService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Employee model) {
        Integer result = employeeMapper.save(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void delete(Integer integer) {
        Integer result = employeeMapper.delete(integer);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void update(Employee model) {
        Integer result = employeeMapper.update(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public Employee get(Integer integer) {
        Employee employee = employeeMapper.get(integer);
        if (employee == null) {
            throw new ServiceException("获取数据失败");
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employeeList = employeeMapper.findAll();
        if (employeeList != null && employeeList.size() > 0) {
            return employeeList;
        } else {
            throw new ServiceException("获取数据失败");
        }
    }

    @Override
    public List<Employee> findExample(Employee model) {
        List<Employee> employeeList = employeeMapper.findExample(model);
        if (employeeList != null && employeeList.size() > 0) {
            return employeeList;
        } else {
            throw new ServiceException("获取数据失败");
        }
    }
}
