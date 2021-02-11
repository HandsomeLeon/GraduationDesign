package org.design.controller;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.design.model.*;
import org.design.service.AbsenceService;
import org.design.service.ProcessService;
import org.design.service.ReimbursementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.design.service.impl.ProcessServiceImpl.ABSENCE_KEY;
import static org.design.service.impl.ProcessServiceImpl.REIMBURSEMENT_KEY;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Resource
    private ProcessService processService;
    @Resource
    private ReimbursementService reimbursementService;
    @Resource
    private AbsenceService absenceService;

    /**
     * 跳转发布流程页面
     *
     * @return
     */
    @RequestMapping("/savePage")
    public String savePage() {
        return "process_save";
    }

    /**
     * 部署流程
     *
     * @param file
     * @param processName
     * @return
     * @throws IOException
     */
    @RequestMapping("/save")
    @ResponseBody
    public String save(MultipartFile file, String processName) throws IOException {

        processService.save(file.getInputStream(), processName);
        return "success";
    }

    @RequestMapping("/find")
    public String find() {
        return "process_list";
    }

    @RequestMapping("/findDeploymentList")
    @ResponseBody
    public Map<String, Object> findDeploymentList(Integer page, Integer limit) {
        return processService.findDeploymentList(page, limit);
    }

    @RequestMapping("/findDefinitionList")
    @ResponseBody
    public Map<String, Object> findDefinitionList(Integer page, Integer limit) {
        return processService.findDefinitionList(page, limit);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> data) {
        String id = (String) data.get("id");
        processService.delete(id);
        return "success";
    }

    @RequestMapping("/findProcessImage")
    public void findProcessImage(String id, String imageName, HttpServletResponse response) throws IOException {
        InputStream inputStream = processService.findProcessImage(id, imageName);
        ServletOutputStream out = response.getOutputStream();
        for (int b = -1; (b = inputStream.read()) != -1; ) {
            out.write(b);
        }
        out.flush();
        out.close();
        inputStream.close();
    }

    @RequestMapping("/reimbursementTaskListPage")
    public String reimbursementTaskListPage() {
        return "reimbursementTask_list";
    }

    @RequestMapping("/absenceTaskListPage")
    public String absenceTaskListPage() {
        return "absenceTask_list";
    }

    @RequestMapping("findReimbursementTaskList")
    @ResponseBody
    public Map<String, Object> findReimbursementTaskList() {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        List<CustomizeTask> taskList = processService.findTaskListByBusinessKey(employee.getUsername(), REIMBURSEMENT_KEY);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", taskList.size());
        data.put("data", taskList);
        return data;
    }

    @RequestMapping("findAbsenceTaskList")
    @ResponseBody
    public Map<String, Object> findAbsenceTaskList() {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        List<CustomizeTask> taskList = processService.findTaskListByBusinessKey(employee.getUsername(), ABSENCE_KEY);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", taskList.size());
        data.put("data", taskList);
        return data;
    }

    @RequestMapping("/reimbursementTaskPage/{taskId}")
    public String reimbursementTaskPage(@PathVariable String taskId, Model model) {
        String reimbursementId = processService.findApplication(taskId);
        Reimbursement reimbursement = reimbursementService.get(Integer.parseInt(reimbursementId));
        List<String> flowDirectionList = processService.findFlowDirectionList(taskId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("reimbursement", reimbursement);
        model.addAttribute("flowDirectionList", flowDirectionList);
        return "reimbursement_update";
    }

    @RequestMapping("/absenceTaskPage/{taskId}")
    public String absenceTaskPage(@PathVariable String taskId, Model model) {
        String absenceId = processService.findApplication(taskId);
        Absence absence = absenceService.get(Integer.parseInt(absenceId));
        List<String> flowDirectionList = processService.findFlowDirectionList(taskId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("absence", absence);
        model.addAttribute("flowDirectionList", flowDirectionList);
        return "absence_update";
    }

    @RequestMapping("/findCommentList")
    @ResponseBody
    public Map<String, Object> findCommentList(String taskId) {
        List<CustomizeComment> commentList = processService.findCommentList(taskId);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", commentList.size());
        data.put("data", commentList);
        return data;
    }

    @RequestMapping("/pushProcess")
    @ResponseBody
    public String pushProcess(@RequestBody Map<String, Object> param) {
        String taskId = (String) param.get("taskId");
        String comment = (String) param.get("comment");
        String flowDirection = (String) param.get("flowDirection");
        String reimbursementId = (String) param.get("reimbursementId");
        String absenceId = (String) param.get("absenceId");

        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        if (reimbursementId != null) {
            processService.pushProcess(taskId, employee.getUsername(), comment, flowDirection, reimbursementId);
        }
        if (absenceId != null) {
            processService.pushProcess(taskId, employee.getUsername(), comment, flowDirection, absenceId);
        }
        return "success";
    }

    @RequestMapping("/findReimbursementRecord/{reimbursementId}")
    public String findReimbursementRecord(@PathVariable Integer reimbursementId, Model model) {
        Reimbursement reimbursement = reimbursementService.get(reimbursementId);
        List<CustomizeComment> commentList;
        // 报销单审核状态为正在审核中
        if (reimbursement.getState() != 2) {
            Task task = processService.findTaskByApplicationId(reimbursementId, REIMBURSEMENT_KEY);
            commentList = processService.findCommentList(task.getId());
        } else {
            commentList = processService.findHistoricalCommentList(reimbursementId, REIMBURSEMENT_KEY);
        }
        model.addAttribute("reimbursement", reimbursement);
        model.addAttribute("commentList", commentList);
        return "reimbursement_record";
    }

    @RequestMapping("/findAbsenceRecord/{absenceId}")
    public String findAbsenceRecord(@PathVariable Integer absenceId, Model model) {
        Absence absence = absenceService.get(absenceId);
        List<CustomizeComment> commentList;
        // 报销单审核状态为正在审核中
        if (absence.getState() != 2) {
            Task task = processService.findTaskByApplicationId(absenceId, ABSENCE_KEY);
            commentList = processService.findCommentList(task.getId());
        } else {
            commentList = processService.findHistoricalCommentList(absenceId, ABSENCE_KEY);
        }
        model.addAttribute("absence", absence);
        model.addAttribute("commentList", commentList);
        return "absence_record";
    }

    @RequestMapping("/findHistoricalReimbursementCommentList")
    @ResponseBody
    public Map<String, Object> findHistoricalReimbursementCommentList(Integer reimbursementId) {
        Reimbursement reimbursement = reimbursementService.get(reimbursementId);
        Task task = processService.findTaskByApplicationId(reimbursementId, REIMBURSEMENT_KEY);
        List<CustomizeComment> commentList;
        // 报销单审核状态为正在审核中
        if (reimbursement.getState() != 2) {
            commentList = processService.findCommentList(task.getId());
        } else {
            commentList = processService.findHistoricalCommentList(reimbursementId, REIMBURSEMENT_KEY);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", commentList.size());
        data.put("data", commentList);
        return data;
    }

    @RequestMapping("/findHistoricalAbsenceCommentList")
    @ResponseBody
    public Map<String, Object> findHistoricalAbsenceCommentList(Integer absenceId) {
        Absence absence = absenceService.get(absenceId);
        Task task = processService.findTaskByApplicationId(absenceId, ABSENCE_KEY);
        List<CustomizeComment> commentList;
        // 报销单审核状态为正在审核中
        if (absence.getState() != 2) {
            commentList = processService.findCommentList(task.getId());
        } else {
            commentList = processService.findHistoricalCommentList(absenceId, ABSENCE_KEY);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", commentList.size());
        data.put("data", commentList);
        return data;
    }

    @RequestMapping("/findCurrentProcessImage/{taskId}")
    public String findCurrentProcessImage(@PathVariable String taskId, Model model) {
        ProcessDefinition processDefinition = processService.findProcessDefinition(taskId);
        Map<String, Object> coordinatesMap = processService.findCurrentProcessCoordinates(taskId);
        System.out.println(processDefinition.getDeploymentId());
        System.out.println(processDefinition.getDiagramResourceName());
        model.addAttribute("deploymentId", processDefinition.getDeploymentId());
        model.addAttribute("imageName", processDefinition.getDiagramResourceName());
        model.addAttribute("acs", coordinatesMap);
        return "process_image";
    }

    @RequestMapping("/findCurrentProcessImageByReimbursementId/{reimbursementId}")
    public String findCurrentProcessImageByReimbursementId(@PathVariable Integer reimbursementId, Model model) {
        Task task = processService.findTaskByApplicationId(reimbursementId, REIMBURSEMENT_KEY);
        ProcessDefinition processDefinition = processService.findProcessDefinition(task.getId());
        Map<String, Object> coordinatesMap = processService.findCurrentProcessCoordinates(task.getId());
        System.out.println(processDefinition.getDeploymentId());
        System.out.println(processDefinition.getDiagramResourceName());
        model.addAttribute("deploymentId", processDefinition.getDeploymentId());
        model.addAttribute("imageName", processDefinition.getDiagramResourceName());
        model.addAttribute("acs", coordinatesMap);
        return "process_image";
    }

    @RequestMapping("/findCurrentProcessImageByAbsenceId/{absenceId}")
    public String findCurrentProcessImageByAbsenceId(@PathVariable Integer absenceId, Model model) {
        Task task = processService.findTaskByApplicationId(absenceId, ABSENCE_KEY);
        ProcessDefinition processDefinition = processService.findProcessDefinition(task.getId());
        Map<String, Object> coordinatesMap = processService.findCurrentProcessCoordinates(task.getId());
        model.addAttribute("deploymentId", processDefinition.getDeploymentId());
        model.addAttribute("imageName", processDefinition.getDiagramResourceName());
        model.addAttribute("acs", coordinatesMap);
        return "process_image";
    }

}
