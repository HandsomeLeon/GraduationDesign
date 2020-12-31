package org.design.service.impl;

import org.design.mapper.DepartmentMapper;
import org.design.model.Department;
import org.design.service.DepartmentService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department model) {
        Integer result = departmentMapper.save(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void delete(Integer integer) {
        Integer result = departmentMapper.delete(integer);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void update(Department model) {
        Integer result = departmentMapper.update(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public Department get(Integer integer) {

        Department department = departmentMapper.get(integer);
        if (department == null) {
            throw new ServiceException("获取数据失败");
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departmentList = departmentMapper.findAll();
        if (departmentList != null && departmentList.size() > 0) {
            return departmentList;
        } else {
            throw new ServiceException("获取数据失败");
        }
    }

    @Override
    public List<Department> findExample(Department model) {

        List<Department> departmentList = departmentMapper.findExample(model);
        if (departmentList != null && departmentList.size() > 0) {
            return departmentList;
        } else {
            throw new ServiceException("获取数据失败");
        }
    }
}
