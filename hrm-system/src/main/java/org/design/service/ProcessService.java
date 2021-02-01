package org.design.service;

import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.design.model.CustomizeComment;
import org.design.model.CustomizeTask;
import org.design.model.Reimbursement;

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

    List<CustomizeTask> findTaskList(String username);

    String findReimbursement(String taskId);

    List<CustomizeComment> findCommentList(String taskId);

    List<String> findFlowDirectionList(String taskId);
}
