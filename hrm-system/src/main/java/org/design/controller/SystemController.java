package org.design.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/system")
public class SystemController {

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
                model.addAttribute("errorMsg", "服务器错误！");
            }

        }
        return "login";
    }

    // 登录成功后跳转页面
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
