package org.design.controller;

import org.design.model.Employee;
import org.design.model.Permission;
import org.design.model.Role;
import org.design.service.EmployeeService;
import org.design.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;
    @Resource
    private SystemService systemService;

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

    @RequestMapping("/find")
    public String find(Model model) {
        model.addAttribute("roleList", systemService.findRoleList());
        return "employee_list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer limit) {
        return employeeService.findAll(page, limit);
    }

    @RequestMapping("/findExample")
    @ResponseBody
    public Map<String, Object> findExample(String username, Integer page, Integer limit) {
        Employee employee = new Employee();
        employee.setUsername(username);
        return employeeService.findExample(employee, page, limit);
    }

    @RequestMapping("/findRole/{roleId}")
    public String findRole(@PathVariable Integer roleId, Model model) {

        Role role = systemService.findRole(roleId);
        model.addAttribute("role", role);
        List<Permission> permissionList = role.getPermissionList();
        List<String> permissionStrList = new ArrayList<>();
        for (Permission permission : permissionList) {
            permissionStrList.add(permission.getName() + " [" + permission.getType() + "]");
        }
        model.addAttribute("permissionList", permissionStrList);
        return "role_permission";
    }
}
