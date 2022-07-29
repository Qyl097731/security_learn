package com.example.demo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qyl
 * @program LoginController.java
 * @Description TODO
 * @createTime 2022-07-14 13:20
 */
@Controller
public class LoginController {

    @ApiOperation(value = "toLogin")
    @GetMapping("toLogin")
    //在执行前先检查是否具有hello权限
    public String toLogin() {
        return "login";
    }
}
