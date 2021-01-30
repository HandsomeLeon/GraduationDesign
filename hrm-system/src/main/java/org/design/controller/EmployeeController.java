package org.design.controller;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @RequestMapping("/updatePage/{id}")
    public String updatePage(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.get(id);
        Employee manager = employeeService.get(employee.getManagerId());
        model.addAttribute("employee", employee);
        model.addAttribute("manager", manager);
        model.addAttribute("departmentList", departmentService.findDepartmentList());
        model.addAttribute("jobList", jobService.findJobList());
        model.addAttribute("roleList", systemService.findRoleList());
        model.addAttribute("timestamp", employee.getBirthday().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return "employee_update";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return "success";
    }

    @RequestMapping("/passwordPage/{id}")
    public String passwordPage(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return "password_update";
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public String updatePassword(@RequestBody Map<String, Object> data) {
        String id = (String) data.get("id");
        String oldPassword = (String) data.get("oldPassword");
        String newPassword = (String) data.get("newPassword");
        Employee employee = employeeService.get(Integer.parseInt(id));
        if (!employee.getPassword().equals(oldPassword)) {
            return "fail";
        }
        employeeService.updatePassword(id, newPassword);
        return "success";
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

    @RequestMapping("/findManager")
    @ResponseBody
    public Map<String, Object> findManager(@RequestBody Map<String, Object> param) {

        Integer roleId = Integer.parseInt(param.get("roleId").toString());
        System.out.println("角色ID：" + roleId);
        List<Employee> employeeList = employeeService.findManagerByRoleId(roleId);
        Map<String, Object> data = new HashMap<>();
        data.put("data", employeeList);
        return data;
    }

}
