package com.example.demo.controller;

import com.example.demo.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qyl
 * @program HelloController.java
 * @Description Test for diff auths
 * @createTime 2022-07-12 10:24
 */
@RestController
@RequestMapping("/test")
public class TestController {

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
