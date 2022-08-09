package com.example.demo.controller;

import com.example.demo.entity.User;
//import com.example.demo.service.UserService;
import com.example.demo.service.UserService;
import com.example.demo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author qyl
 * @program IndexController.java
 * @Description 登录
 * @createTime 2022-07-28 09:34
 */
@RestController
public class IndexController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    /**
     * @description 注册账号
     * @param user
     * @return R
     * @author qyl
     * @date 2022/8/9 11:17
     */
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

    /**
     * @description 查看是否session共享
     * @param request
     * @return R
     * @author qyl
     * @date 2022/8/9 11:18
     */
    @GetMapping("/session")
    public R session(HttpServletRequest request) {
        System.out.println("session: " + request.getSession().getId() + "  port: " + request.getServerPort());
        return R.ok().message("session: " + request.getSession().getId() + "  port: " + request.getServerPort());
    }
}

