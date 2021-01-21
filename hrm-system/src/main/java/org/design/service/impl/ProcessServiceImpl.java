package org.design.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.design.model.CustomizeDeployment;
import org.design.model.CustomizeProcessDefinition;
import org.design.service.AbsenceService;
import org.design.service.ProcessService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

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
    public void pushProcess(Integer id, String username) {

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

}
