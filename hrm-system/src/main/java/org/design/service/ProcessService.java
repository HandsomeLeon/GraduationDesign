package org.design.service;

import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ProcessService {

    /**
     * 部署流程
     * @param inputStream
     * @param processName
     */
    void save(InputStream inputStream, String processName);

    void pushProcess(Integer id, String username);

    Map<String, Object> findDeploymentList(Integer page, Integer limit);

    Map<String, Object> findDefinitionList(Integer page, Integer limit);

    void delete(String id);

    InputStream findProcessImage(String id, String imageName);
}
