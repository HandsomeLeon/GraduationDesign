package org.design.controller;

import org.design.model.Department;
import org.design.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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

    /*@RequestMapping("/findAll")
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView();
        List<Department> departmentList = departmentService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", departmentList.size());
        data.put("data", departmentList);
        mv.addObject(data);
        mv.setViewName("department_list");
        return mv;
    }*/

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(String page, String limit) {
        System.out.println(page);
        System.out.println(limit);
        List<Department> departmentList = departmentService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", departmentList.size());
        data.put("data", departmentList);
        return data;
    }

    @RequestMapping("/findExample")
    public List<Department> findExample(@RequestBody Department department) {
        return departmentService.findExample(department);
    }

}
