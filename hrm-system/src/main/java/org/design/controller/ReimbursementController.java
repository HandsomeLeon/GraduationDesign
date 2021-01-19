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

        processService.pushProcess(reimbursement.getId(), employee.getUsername());

        return "success";
    }
}
