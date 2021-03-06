package org.design.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.design.mapper.EmployeeMapper;
import org.design.model.Employee;
import org.design.model.Job;
import org.design.service.EmployeeService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "employeeService")
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
        return employee;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {

        List<Employee> allEmployeeList = employeeMapper.findAll();

        PageHelper.startPage(page, limit);
        List<Employee> employeeList = employeeMapper.findAll();
        if (employeeList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);

        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", employeeList);
        data.put("employeeList", allEmployeeList);
        return data;
    }

    @Override
    public Map<String, Object> findExample(Employee model, Integer page, Integer limit) {

        List<Employee> allEmployeeList = employeeMapper.findAll();

        PageHelper.startPage(page, limit);
        List<Employee> employeeList = employeeMapper.findExample(model);
        if (employeeList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);

        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", employeeList);
        data.put("employeeList", allEmployeeList);
        return data;
    }

    @Override
    public Employee findByUsername(String username) {
        Employee employee = employeeMapper.findByUsername(username);
        return employee;
    }

    @Override
    public void updateRole(Integer id, Integer roleId) {
        Integer result = employeeMapper.updateRole(id, roleId);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public List<Employee> findManagerByRoleId(Integer roleId) {

        if (roleId == 6) {

        }
        List<Employee> employeeList = employeeMapper.findManagerByRoleId(roleId);
        if (employeeList == null) {
            throw new ServiceException("获取数据失败");
        }
        return employeeList;
    }

    @Override
    public void updatePassword(String id, String newPassword) {
        Integer result = employeeMapper.updatePassword(id, newPassword);
        if (result <= 0) {
            throw new ServiceException("操作失败");
        }
    }

    @Override
    public Employee findManager(Integer managerId) {
        return get(managerId);
    }
}
