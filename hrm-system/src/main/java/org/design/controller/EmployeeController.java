package org.design.controller;

import org.design.model.Employee;
import org.design.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @RequestMapping("/save")
    public String save(@RequestBody Employee employee) {
        employeeService.save(employee);
        return "新增数据成功";
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        employeeService.delete(id);
        return "删除数据成功";
    }

    @RequestMapping("/update")
    public String update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return "更新数据成功";
    }

    @RequestMapping("/get")
    public Employee get(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        return employeeService.get(id);
    }

    @RequestMapping("/findAll")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @RequestMapping("/findExample")
    public List<Employee> findExample(@RequestBody Employee employee) {
        return employeeService.findExample(employee);
    }
}
