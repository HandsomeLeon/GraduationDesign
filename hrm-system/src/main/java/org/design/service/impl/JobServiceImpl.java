package org.design.service.impl;

import org.design.mapper.JobMapper;
import org.design.model.Employee;
import org.design.model.Job;
import org.design.service.JobService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public List<Job> findAll() {
        List<Job> jobList = jobMapper.findAll();
        if (jobList != null && jobList.size() > 0) {
            return jobList;
        } else {
            throw new ServiceException("获取数据失败");
        }
    }

    @Override
    public List<Job> findExample(Job model) {
        List<Job> jobList = jobMapper.findExample(model);
        if (jobList != null && jobList.size() > 0) {
            return jobList;
        } else {
            throw new ServiceException("获取数据失败");
        }
    }
}
