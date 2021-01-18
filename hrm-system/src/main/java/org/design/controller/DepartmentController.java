package org.design.controller;

import org.design.model.Department;
import org.design.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @RequestMapping("/savePage")
    public String savePage() {
        return "department_save";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody Department department) {
        departmentService.save(department);
        return "success";
    }

    /**
     * 若是使用POST方法传输，前端Json传入map 获取id值
     * @param data
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        departmentService.delete(id);
        return "success";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            System.out.println(id);
            departmentService.delete(id);
        }
        return "success";
    }

    @RequestMapping("/updatePage/{id}")
    public String check(@PathVariable Integer id, Model model) {
        System.out.println(id);
        Department department = departmentService.get(id);
        model.addAttribute("department", department);
        return "department_update";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestBody Department department) {
        System.out.println(department);
        departmentService.update(department);
        return "success";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Department get(@RequestBody Map<String, Object> data) {

        Integer id = (Integer) data.get("id");
        Department department = departmentService.get(id);
        return department;
    }

    @RequestMapping("/find")
    public String findDepartment() {
        return "department_list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer limit) {
        Map<String, Object> data = departmentService.findAll(page, limit);
        return data;
    }

    @RequestMapping("/findExample")
    @ResponseBody
    public Map<String, Object> findExample(String name, Integer page, Integer limit) {
        Department department = new Department();
        department.setName(name);
        return departmentService.findExample(department, page, limit);
    }

}
