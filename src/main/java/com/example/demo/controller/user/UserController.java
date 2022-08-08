package com.example.demo.controller.user;

import com.example.demo.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author qyl
 * @program UserController.java
 * @Description 用户管理
 * @createTime 2022-07-29 11:05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    FindByIndexNameSessionRepository sessionRepository;

    @ApiOperation(value = "manage")
    @PostMapping("manage")
    //在执行前先检查是否具有manage权限
    @PreAuthorize("hasAuthority('user.manage')")
    public R manage() {
        return R.ok().data("msg", "user manage");
    }

    @ApiOperation(value = "info")
    @PostMapping("info")
    //在执行前先检查是否具有manage权限
//    @PreAuthorize("hasAuthority('user.info')")
    public R info() {
        Map admin = sessionRepository.findByPrincipalName("spring:session:index:org.springframework.session.FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME");
        return R.ok().data("admin", admin);
    }

}
