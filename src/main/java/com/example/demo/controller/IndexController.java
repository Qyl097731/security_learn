package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author qyl
 * @program IndexController.java
 * @Description 登录
 * @createTime 2022-07-28 09:34
 */
@Controller
public class IndexController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/register")
    public R register(User user){
        if(null != userService.selectByUsername(user.getUsername())){
            return R.error().message("用户已经存在");
        }
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return R.ok().message("注册成功");
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

}

