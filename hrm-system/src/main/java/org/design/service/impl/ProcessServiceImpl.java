package org.design.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.design.model.*;
import org.design.service.AbsenceService;
import org.design.service.ProcessService;
import org.design.service.ReimbursementService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private AbsenceService absenceService;
    @Resource
    private ReimbursementService reimbursementService;


    @Override
    public void save(InputStream inputStream, String processName) {

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        try {
            repositoryService
                    .createDeployment()
                    .name(processName)
                    .addZipInputStream(zipInputStream)
                    .deploy();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("部署流程出错");
        }
    }

    @Override
    public void startProcess(Integer id, String username) {

        Map<String, Object> map = new HashMap<>();

        String key = "baoxiaoprocess";
        String BUSINESS_KEY = key + "." + id.toString();
        map.put("inputUser", username);
        map.put("objId", BUSINESS_KEY);

        runtimeService.startProcessInstanceByKey(key, BUSINESS_KEY, map);
    }

    @Override
    public Map<String, Object> findDeploymentList(Integer page, Integer limit) {

        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().orderByDeploymentId().asc().list();

        List<CustomizeDeployment> customizeDeploymentList = new ArrayList<>();
        for (Deployment deployment : deploymentList) {
            CustomizeDeployment customizeDeployment = new CustomizeDeployment();
            customizeDeployment.setId(deployment.getId());
            customizeDeployment.setName(deployment.getName());
            customizeDeployment.setDeploymentTime(deployment.getDeploymentTime());
            customizeDeploymentList.add(customizeDeployment);
        }
        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", deploymentList.size());
        data.put("data", customizeDeploymentList);
        return data;
    }

    @Override
    public Map<String, Object> findDefinitionList(Integer page, Integer limit) {

        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().asc().list();
        List<CustomizeProcessDefinition> customizeProcessDefinitionList = new ArrayList<>();
        for (ProcessDefinition processDefinition : definitionList) {
            CustomizeProcessDefinition definition = new CustomizeProcessDefinition();
            definition.setId(processDefinition.getId());
            definition.setName(processDefinition.getName());
            definition.setKey(processDefinition.getKey());
            definition.setVersion(processDefinition.getVersion());
            definition.setResourceName(processDefinition.getResourceName());
            definition.setDiagramResourceName(processDefinition.getDiagramResourceName());
            definition.setDeploymentId(processDefinition.getDeploymentId());
            customizeProcessDefinitionList.add(definition);
        }
        // 封装LayUI需要的数据格式
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", definitionList.size());
        data.put("data", customizeProcessDefinitionList);
        return data;
    }

    @Override
    public void delete(String id) {
        try {
            repositoryService.deleteDeployment(id, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public InputStream findProcessImage(String id, String imageName) {

        return repositoryService.getResourceAsStream(id, imageName);
    }

    @Override
    public List<CustomizeTask> findTaskList(String username) {

        List<Task> taskList = this.taskService
                .createTaskQuery().taskAssignee(username).orderByTaskCreateTime().desc().list();
        List<CustomizeTask> customizeTaskList = new ArrayList<>();
        for (Task task : taskList) {
            CustomizeTask customizeTask = new CustomizeTask();
            customizeTask.setId(task.getId());
            customizeTask.setName(task.getName());
            customizeTask.setAssignee(task.getAssignee());
            customizeTask.setCreateTime(task.getCreateTime());
            customizeTaskList.add(customizeTask);
        }
        return customizeTaskList;
    }

    @Override
    public String findReimbursement(String taskId) {

        Task task = this.taskService
                .createTaskQuery().taskId(taskId).singleResult();

        ProcessInstance processInstance = this.runtimeService
                .createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();

        String businessKey = processInstance.getBusinessKey();

        String id = businessKey.substring(businessKey.lastIndexOf(".") + 1);

        return id;
    }

    @Override
    public List<CustomizeComment> findCommentList(String taskId) {

        Task task = this.taskService
                .createTaskQuery().taskId(taskId).singleResult();

        List<Comment> commentList = taskService.getProcessInstanceComments(task.getProcessInstanceId());
        List<CustomizeComment> customizeCommentList = new ArrayList<>();
        for (Comment comment : commentList) {
            CustomizeComment customizeComment = new CustomizeComment();
            customizeComment.setId(comment.getId());
            customizeComment.setTime(comment.getTime());
            customizeComment.setUserId(comment.getUserId());
            customizeComment.setFullMessage(comment.getFullMessage());
            customizeCommentList.add(customizeComment);
        }

        return customizeCommentList;
    }

    @Override
    public List<String> findFlowDirectionList(String taskId) {
        //·创建 部门经理 提交流程的所有连线
        List<String> flowDirectionList = new ArrayList<>();

        //·获取当前正在执行的任务
        Task task = this.taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();

        //·获取 流程定义 实体
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) this.repositoryService
                .getProcessDefinition(task.getProcessDefinitionId());

        //·获取流程实例
        ProcessInstance processInstance = this.runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();

        //·获取当前活动的id
        String activityId = processInstance.getActivityId();

        //·获取当前的活动
        ActivityImpl activityImpl = definitionEntity.findActivity(activityId);

        //·获取当前活动的所有连线
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();

        if (pvmTransitionList != null && pvmTransitionList.size() > 0) {

            for (PvmTransition pvmTransition : pvmTransitionList) {

                //·获取连线的name
                String lineName = (String) pvmTransition.getProperty("name");

                if (StringUtils.isNotBlank(lineName)) {
                    flowDirectionList.add(lineName);

                } else {
                    flowDirectionList.add("默认提交");
                }
            }
        }
        return flowDirectionList;
    }

    @Transactional
    @Override
    public void pushProcess(String taskId, String username, String comment, String flowDirection, String reimbursementId) {
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        // 流程定义实例ID
        String processInstanceId = task.getProcessInstanceId();
        //·添加批注中批注人名称
        Authentication.setAuthenticatedUserId(username);
        //·添加批注
        this.taskService.addComment(taskId, processInstanceId, comment);
        //·创建  流程变量 message
        Map<String, Object> messageMap = new HashMap<>();
        //·根据所选的按钮  推进流程
        if (flowDirection != null && !"默认提交".equals(flowDirection)) {
            messageMap.put("message", flowDirection);
            //·提交流程 并 设置流程变量
            this.taskService.complete(taskId, messageMap);

        } else { //·普通员工  提交流程
            this.taskService.complete(taskId);
        }
        //·推进完流程之后，判断流程是否结束
        ProcessInstance processInstance = this.runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        //·流程结束后  设置报销表的状态为  结束（即为2）
        if (processInstance == null) {
            this.reimbursementService.updateState(reimbursementId, 2);
        }
    }

    @Override
    public Task findTaskByReimbursementId(Integer reimbursementId) {
        String BUSINESS_KEY = "baoxiaoprocess." + reimbursementId;
        return this.taskService.createTaskQuery().processInstanceBusinessKey(BUSINESS_KEY).singleResult();
    }

    @Override
    public List<CustomizeComment> findHistoricalCommentList(Integer reimbursementId) {
        String BUSINESS_KEY = "baoxiaoprocess." + reimbursementId;
        HistoricProcessInstance historicProcessInstance = this.historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(BUSINESS_KEY)
                .singleResult();
        List<Comment> commentList = this.taskService.getProcessInstanceComments(historicProcessInstance.getId());
        List<CustomizeComment> customizeCommentList = new ArrayList<>();
        for (Comment comment : commentList) {
            CustomizeComment customizeComment = new CustomizeComment();
            customizeComment.setId(comment.getId());
            customizeComment.setTime(comment.getTime());
            customizeComment.setUserId(comment.getUserId());
            customizeComment.setFullMessage(comment.getFullMessage());
            customizeCommentList.add(customizeComment);
        }
        return customizeCommentList;
    }

    @Override
    public ProcessDefinition findProcessDefinition(String taskId) {
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessDefinition processDefinition = this.repositoryService
                .createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        return processDefinition;
    }

    @Override
    public Map<String, Object> findCurrentProcessCoordinates(String taskId) {
        Map<String, Object> coordinatesMap = new HashMap<>();
        //·获取当前正在执行的任务
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //·获取 流程定义 实体
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) this.repositoryService
                .getProcessDefinition(task.getProcessDefinitionId());
        //·获取流程实例
        ProcessInstance processInstance = this.runtimeService
                .createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //·获取当前活动的id
        String activityId = processInstance.getActivityId();
        //·获取当前的活动
        ActivityImpl activityImpl = definitionEntity.findActivity(activityId);
        if (activityImpl != null) {
            coordinatesMap.put("x", activityImpl.getX());
            coordinatesMap.put("y", activityImpl.getY());
            coordinatesMap.put("width", activityImpl.getWidth());
            coordinatesMap.put("height", activityImpl.getHeight());
        }
        return coordinatesMap;
    }

}
