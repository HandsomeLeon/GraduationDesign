package org.design.controller;

import javafx.scene.input.DataFormat;
import org.design.model.Employee;
import org.design.model.Permission;
import org.design.model.Role;
import org.design.service.DepartmentService;
import org.design.service.EmployeeService;
import org.design.service.JobService;
import org.design.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;
    @Resource
    private SystemService systemService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private JobService jobService;

    @RequestMapping("/savePage")
    public String savePage(Model model) {
        model.addAttribute("departmentList", departmentService.findDepartmentList());
        model.addAttribute("jobList", jobService.findJobList());
        model.addAttribute("roleList", systemService.findRoleList());
        return "employee_save";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody Employee employee) {
        employee.setCreateTime(LocalDateTime.now());
        employeeService.save(employee);
        return "success";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        employeeService.delete(id);
        return "success";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return "success";
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

    @RequestMapping("/updateRole")
    @ResponseBody
    public String updateRole(@RequestBody Map<String, Object> data) {
        employeeService.updateRole(Integer.parseInt(data.get("id").toString()), Integer.parseInt(data.get("roleId").toString()));
        return "success";
    }

}
