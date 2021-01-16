package org.design.service.impl;

import org.design.mapper.EmployeeMapper;
import org.design.model.Employee;
import org.design.service.EmployeeService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> findAll(Integer page, Integer limit) {
        List<Employee> employeeList = employeeMapper.findAll();
        if (employeeList == null) {
            throw new ServiceException("获取数据失败");
        }
        return null;
    }

    @Override
    public Map<String, Object> findExample(Employee model, Integer page, Integer limit) {
        List<Employee> employeeList = employeeMapper.findExample(model);
        if (employeeList == null) {
            throw new ServiceException("获取数据失败");
        }
        return null;
    }

    @Override
    public Employee findByUsername(String username) {

        Employee employee = employeeMapper.findByUsername(username);
        if (employee == null) {
            throw new ServiceException("获取数据失败");
        }
        return employee;
    }
}
