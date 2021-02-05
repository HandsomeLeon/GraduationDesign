package org.design.mapper;

import org.design.model.Department;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DepartmentMapper {

    Integer save(Department department);

    Integer delete(Integer id);

    Integer update(Department department);

    Department get(Integer id);

    List<Department> findAll();

    List<Department> findExample(Department department);
}
