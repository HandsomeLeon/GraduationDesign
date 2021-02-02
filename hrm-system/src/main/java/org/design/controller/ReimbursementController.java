package org.design.controller;

import org.apache.shiro.SecurityUtils;
import org.design.model.Employee;
import org.design.model.Reimbursement;
import org.design.service.ProcessService;
import org.design.service.ReimbursementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reimbursement")
public class ReimbursementController {

    @Resource
    private ReimbursementService reimbursementService;
    @Resource
    private ProcessService processService;

    @RequestMapping("/savePage")
    public String savePage() {
        return "reimbursement_save";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody Reimbursement reimbursement) {

        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();

        reimbursement.setState(1);
        reimbursement.setCreateTime(LocalDateTime.now());
        reimbursement.setUserId(Long.parseLong(employee.getId().toString()));

        reimbursementService.save(reimbursement);
        processService.startProcess(reimbursement.getId(), employee.getUsername());
        return "success";
    }

    @RequestMapping("/find")
    public String findReimbursementList() {
        return "reimbursement_list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer limit) {
        return reimbursementService.findAll(page, limit);
    }

    @RequestMapping("/findAuthenticatedReimbursementList")
    @ResponseBody
    public Map<String, Object> findAuthenticatedReimbursementList(Integer page, Integer limit) {
        return reimbursementService.findByState(page, limit, 2);
    }

    @RequestMapping("/findNotAuthenticateReimbursementList")
    @ResponseBody
    public Map<String, Object> findNotAuthenticateReimbursementList(Integer page, Integer limit) {
        return reimbursementService.findByState(page, limit, 1);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> data) {

        Integer id = (Integer) data.get("id");
        reimbursementService.delete(id);
        return "success";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            System.out.println(id);
            reimbursementService.delete(id);
        }
        return "success";
    }

}
