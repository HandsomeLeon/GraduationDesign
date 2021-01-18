package org.design.controller;

import org.design.service.ProcessService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Resource
    private ProcessService processService;

    /**
     * 跳转发布流程页面
     * @return
     */
    @RequestMapping("/savePage")
    public String savePage() {
        return "process_save";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(MultipartFile file, String processName) {

        if (file.isEmpty()) {
            throw new ServiceException("文件不能为空");
        }
        return "success";
    }
}
