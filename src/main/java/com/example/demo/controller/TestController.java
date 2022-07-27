package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author qyl
 * @program HelloController.java
 * @Description Test for diff auths
 * @createTime 2022-07-12 10:24
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "register")
    @PostMapping("register")
    //在执行前先检查是否具有manage权限
    public R register(@RequestBody User user) {
        return null;
    }

    @ApiOperation(value = "login")
    @PostMapping("login")
    //在执行前先检查是否具有manage权限
    public R login(@RequestBody User user) {
        return null;
    }


    @ApiOperation(value = "hello")
    @PostMapping("hello")
    //在执行前先检查是否具有hello权限
    @PreAuthorize("hasAuthority('hello')")
    public R hello() {
        return R.ok().data("hello", "hello");
    }

    @ApiOperation(value = "manage")
    @PostMapping("manage")
    //在执行前先检查是否具有manage权限
    @PreAuthorize("hasAuthority('manage')")
    public R manage() {
        return R.ok().data("manage", "manage");
    }
}
