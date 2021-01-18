package org.design.controller;

import org.design.model.Department;
import org.design.model.Job;
import org.design.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/job")
public class JobController {

    @Resource
    private JobService jobService;

    @RequestMapping("/savePage")
    public String savePage() {
        return "job_save";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody Job job) {
        jobService.save(job);
        return "success";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        jobService.delete(id);
        return "success";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            System.out.println(id);
            jobService.delete(id);
        }
        return "success";
    }

    @RequestMapping("/updatePage/{id}")
    public String check(@PathVariable Integer id, Model model) {
        System.out.println(id);
        Job job = jobService.get(id);
        model.addAttribute("job", job);
        return "job_update";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestBody Job job) {
        jobService.update(job);
        return "success";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Job get(@RequestBody Map<String, Object> data) {

        Integer id = (Integer) data.get("id");
        return jobService.get(id);
    }

    @RequestMapping("/find")
    public String findDepartment() {
        return "job_list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer limit) {
        return jobService.findAll(page, limit);
    }

    @RequestMapping("/findExample")
    @ResponseBody
    public Map<String, Object> findExample(String name, Integer page, Integer limit) {
        Job job = new Job();
        job.setName(name);
        return jobService.findExample(job, page, limit);
    }
}
