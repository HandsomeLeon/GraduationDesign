package org.design.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.design.mapper.DepartmentMapper;
import org.design.model.Department;
import org.design.service.DepartmentService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> findAll(Integer page, Integer limit) {

        // 开启分页
        PageHelper.startPage(page, limit);
        List<Department> departmentList = departmentMapper.findAll();
        if (departmentList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Department> pageInfo = new PageInfo<>(departmentList);
        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", departmentList);
        return data;
    }

    @Override
    public Map<String, Object> findExample(Department model, Integer page, Integer limit) {

        // 开启分页
        PageHelper.startPage(page, limit);
        List<Department> departmentList = departmentMapper.findExample(model);
        if (departmentList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Department> pageInfo = new PageInfo<>(departmentList);
        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", departmentList);
        return data;
    }

    @Override
    public List<Department> findDepartmentList() {

        List<Department> departmentList = departmentMapper.findAll();
        if (departmentList == null) {
            throw new ServiceException("获取数据失败");
        }
        return departmentList;
    }
}
