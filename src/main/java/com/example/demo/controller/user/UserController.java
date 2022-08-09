package com.example.demo.controller.user;

import com.example.demo.entity.SecurityUser;
import com.example.demo.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

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
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "manage")
    @GetMapping("manage")
    //在执行前先检查是否具有manage权限
    @PreAuthorize("hasAuthority('user.manage')")
    public R manage() {
        return R.ok().data("msg", "user manage");
    }

    @ApiOperation(value = "info")
    @GetMapping("info")
    //在执行前先检查是否具有manage权限
    @PreAuthorize("hasAuthority('user.info')")
    public R info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return R.ok().data("user",user);
    }

}
