package org.design.service;

import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
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

    void startProcess(Integer absenceId, String username, String processKey);

    Map<String, Object> findDeploymentList(Integer page, Integer limit);

    Map<String, Object> findDefinitionList(Integer page, Integer limit);

    void delete(String id);

    InputStream findProcessImage(String id, String imageName);

    List<CustomizeTask> findTaskListByBusinessKey(String username, String businessKey);

    String findApplication(String taskId);

    List<CustomizeComment> findCommentList(String taskId);

    List<String> findFlowDirectionList(String taskId);

    void pushProcess(String taskId, String username, String comment, String flowDirection, String applicationId);

    Task findTaskByApplicationId(Integer applicationId, String processKey);

    List<CustomizeComment> findHistoricalCommentList(Integer applicationId, String processKey);

    ProcessDefinition findProcessDefinition(String taskId);

    Map<String, Object> findCurrentProcessCoordinates(String taskId);
}
