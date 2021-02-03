package org.design.controller;

import com.google.code.kaptcha.Constants;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.design.model.Employee;
import org.design.model.MenuTree;
import org.design.model.Role;
import org.design.service.SystemService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SystemController {

    @Resource
    private SystemService systemService;

    /*@RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        System.out.println("正在进行登录操作");
        String message = (String) request.getAttribute("shiroLoginFailure");
        System.out.println(message);
        if (message != null) {
            if (UnknownAccountException.class.getName().equals(message)) {
                model.addAttribute("errorMsg", "账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(message)) {
                model.addAttribute("errorMsg", "密码不正确");
            } else if ("验证码错误".equals(message)) {
                model.addAttribute("errorMsg", "验证码错误");
            } else {
                model.addAttribute("errorMsg", "服务器错误");
            }
        }
        return "login";
    }*/

    @RequestMapping("/login")
    public String login(String username, String password, String captchaCode, Model model) {
        System.out.println("正在进行登录操作");

        System.out.println(username);
        System.out.println(password);
        System.out.println(captchaCode);
        Subject subject = SecurityUtils.getSubject();
        Session session = SecurityUtils.getSubject().getSession();
        UsernamePasswordToken passwordToken = new UsernamePasswordToken(username, password);
        String validateCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (captchaCode != null && validateCode != null) {
            if (!captchaCode.equals(validateCode)) {
                model.addAttribute("errorMsg", "验证码错误");
                return "login";
            }
        }
        if (passwordToken.getUsername() != null) {
            try {
                subject.login(passwordToken);
            } catch (UnknownAccountException e) {
                model.addAttribute("errorMsg", "账号不存在");
            } catch (IncorrectCredentialsException e) {
                model.addAttribute("errorMsg", "密码不正确");
            } catch (Exception e) {
                model.addAttribute("errorMsg", "服务器错误");
            }
        }
        return "login";
    }

    /**
     * 主页
     *
     * @param model
     * @return 返回 menuTreeList
     */
    @RequestMapping("/index")
    public String index(Model model) {
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        List<MenuTree> menuTreeList = systemService.findMenuTreeList(employee.getUsername());
        Employee info = new Employee();
        info.setId(employee.getId());
        info.setUsername(employee.getUsername());
        model.addAttribute("employee", info);
        model.addAttribute("menuTreeList", menuTreeList);
        return "index";
    }

    @RequestMapping("/system/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/role/find")
    public String findRoleList() {
        return "role_list";
    }

    @RequestMapping("/role/findAll")
    @ResponseBody
    public Map<String, Object> findAll() {
        List<Role> roleList = systemService.findRoleList();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", roleList.size());
        data.put("data", roleList);
        return data;
    }

    @RequestMapping("/role/updatePage/{roleId}")
    public String updatePage(@PathVariable Integer roleId, Model model) {
        model.addAttribute("permissionList", systemService.findAllPermission());
        model.addAttribute("roleId", roleId);
        return "role_update";
    }

    @RequestMapping("/role/findPermissionIdList")
    @ResponseBody
    public List<Integer> findPermissionByRoleId(@RequestBody Map<String, Object> data) {
        String roleIdStr = (String) data.get("roleId");
        Integer roleId = Integer.parseInt(roleIdStr);
        return systemService.findPermissionByRoleId(roleId);
    }

    @RequestMapping("/role/update")
    @ResponseBody
    public String update(@RequestBody Map<String, Object> param) {
        List<String> permissionIds = (List<String>) param.get("permissionIds");
        String roleId = (String) param.get("roleId");
        System.out.println(roleId);
        for (String permissionId : permissionIds) {
            System.out.println(permissionId);
        }
        systemService.updateRole(roleId, permissionIds);
        return "success";
    }

}
