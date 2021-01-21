package org.design.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.design.service.ProcessService;
import org.design.utils.ServiceException;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Resource
    private ProcessService processService;

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

    /*@RequestMapping("/findImagePage")
    public String findImagePage(String id, String imageName, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("imageName", imageName);
        return "image";
    }*/

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

}
