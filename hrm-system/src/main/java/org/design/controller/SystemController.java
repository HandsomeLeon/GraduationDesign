package org.design.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.design.model.Employee;
import org.design.model.MenuTree;
import org.design.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
//@RequestMapping("/system")
public class SystemController {

    @Resource
    private SystemService systemService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {

        System.out.println("正在进行登录操作");
        String message = (String) request.getAttribute("shiroLoginFailure");

        if (message != null) {

            if (UnknownAccountException.class.getName().equals(message)) {
                model.addAttribute("errorMsg", "账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(message)) {
                model.addAttribute("errorMsg", "密码不正确");
            } else {
                model.addAttribute("errorMsg", "服务器错误");
            }
        }
        return "login";
    }

    /**
     * 主页
     * @param model
     * @return  返回 menuTreeList
     */
    @RequestMapping("/index")
    public String index(Model model) {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        List<MenuTree> menuTreeList = systemService.findMenuTreeList(employee.getUsername());
        model.addAttribute("menuTreeList", menuTreeList);
        return "index";
    }

}
