package org.design.controller;

import org.design.model.Department;
import org.design.service.DepartmentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @RequestMapping("/save")
    public String save(@RequestBody Department department) {
        departmentService.save(department);
        return "新增数据成功";
    }

    /**
     * 若是使用POST方法传输，前端Json传入map 获取id值
     * @param data
     * @return
     */
    @RequestMapping("/delete")
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        departmentService.delete(id);
        return "删除数据成功";
    }

    @RequestMapping("/update")
    public String update(@RequestBody Department department) {
        departmentService.update(department);
        return "更新数据成功";
    }

    @RequestMapping("/get")
    public Department get(@RequestBody Map<String, Object> data) {

        Integer id = (Integer) data.get("id");
        Department department = departmentService.get(id);
        return department;
    }

    @RequestMapping("/findAll")
    public List<Department> findAll() {
        return departmentService.findAll();
    }

    @RequestMapping("/findExample")
    public List<Department> findExample(@RequestBody Department department) {
        return departmentService.findExample(department);
    }

}
