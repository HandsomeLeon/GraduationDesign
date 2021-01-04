package org.design.controller;

import org.design.model.User;
import org.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index() {
        System.out.println("test");
        return "index";
    }

    @RequestMapping(value = "/save")
    public String save(@RequestBody User user) {
        System.out.println(user);
        userService.save(user);
        return "index";
    }

    @PostMapping(value = "/get")
    @ResponseBody
    public User get(@RequestBody Integer id) {
        return userService.get(id);
    }
}
