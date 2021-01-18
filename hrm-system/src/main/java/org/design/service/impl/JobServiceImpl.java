package org.design.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.design.mapper.JobMapper;
import org.design.model.Department;
import org.design.model.Employee;
import org.design.model.Job;
import org.design.service.JobService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Override
    public void save(Job model) {
        Integer result = jobMapper.save(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void delete(Integer integer) {
        Integer result = jobMapper.delete(integer);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void update(Job model) {
        Integer result = jobMapper.update(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public Job get(Integer integer) {
        Job job = jobMapper.get(integer);
        if (job == null) {
            throw new ServiceException("获取数据失败");
        }
        return job;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {
        // 开启分页
        PageHelper.startPage(page, limit);
        List<Job> jobList = jobMapper.findAll();
        if (jobList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Job> pageInfo = new PageInfo<>(jobList);

        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", pageInfo.getList());
        return data;
    }

    @Override
    public Map<String, Object> findExample(Job model, Integer page, Integer limit) {

        // 开启分页
        PageHelper.startPage(page, limit);
        List<Job> jobList = jobMapper.findExample(model);
        if (jobList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Job> pageInfo = new PageInfo<>(jobList);

        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", pageInfo.getList());
        return data;
    }
}
