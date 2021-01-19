package org.design.service.impl;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.design.service.AbsenceService;
import org.design.service.ProcessService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
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
    public void saveProcess(InputStream inputStream, String processName) {

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
}
