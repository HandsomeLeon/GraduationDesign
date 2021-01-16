package org.design.controller;

import org.design.model.Job;
import org.design.service.JobService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController {

    @Resource
    private JobService jobService;

    @RequestMapping("/save")
    public String save(@RequestBody Job job) {
        jobService.save(job);
        return "新增数据成功";
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        jobService.delete(id);
        return "删除数据成功";
    }

    @RequestMapping("/update")
    public String update(@RequestBody Job job) {
        jobService.update(job);
        return "更新数据成功";
    }

    @RequestMapping("/get")
    public Job get(@RequestBody Map<String, Object> data) {

        Integer id = (Integer) data.get("id");
        Job job = jobService.get(id);
        return job;
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
