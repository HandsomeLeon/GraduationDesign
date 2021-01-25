package org.design.service;

import org.design.model.Job;

import java.util.List;

public interface JobService extends BaseService<Job, Integer> {

    List<Job> findJobList();
}
