package org.design.controller;

import org.apache.shiro.SecurityUtils;
import org.design.model.Absence;
import org.design.model.Employee;
import org.design.service.AbsenceService;
import org.design.service.ProcessService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.design.service.impl.ProcessServiceImpl.ABSENCE_KEY;

@Controller
@RequestMapping("/absence")
public class AbsenceController {

    @Resource
    private AbsenceService absenceService;
    @Resource
    private ProcessService processService;

    @RequestMapping("savePage")
    public String savePage() {
        return "absence_save";
    }

    @Transactional
    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestBody Absence absence) {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        absence.setState(1);
        absence.setUserId(employee.getId());
        absenceService.save(absence);
        processService.startProcess(absence.getId(), employee.getUsername(), ABSENCE_KEY);
        return "success";
    }

    @RequestMapping("/find")
    public String findAbsenceList() {
        return "absence_list";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer limit) {
        return absenceService.findAll(page, limit);
    }

    @RequestMapping("/findAuthenticatedAbsenceList")
    @ResponseBody
    public Map<String, Object> findAuthenticatedAbsenceList(Integer page, Integer limit) {
        return absenceService.findByState(page, limit, 2);
    }

    @RequestMapping("/findNotAuthenticateAbsenceList")
    @ResponseBody
    public Map<String, Object> findNotAuthenticateAbsenceList(Integer page, Integer limit) {
        return absenceService.findByState(page, limit, 1);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        absenceService.delete(id);
        return "success";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            absenceService.delete(id);
        }
        return "success";
    }
}
