package org.design.mapper;

import org.design.model.Job;
import org.design.model.User;

import java.util.List;

public interface JobMapper {

    Integer save(Job job);

    Integer delete(Integer id);

    Integer update(Job job);

    Job get(Integer id);

    List<Job> findAll();

    List<Job> findExample(Job job);
}
