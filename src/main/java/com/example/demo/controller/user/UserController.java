package com.example.demo.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.utils.R;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author qyl
 * @program UserController.java
 * @Description 用户管理
 * @createTime 2022-07-29 11:05
 */
@RestController
@RequestMapping("/nsec/user")
@PreAuthorize("hasRole('system')")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("all/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = false,defaultValue = "0")
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = false,defaultValue = "10")
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!Strings.isNullOrEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }
        userService.page(pageParam, wrapper);
        return R.ok().data("page",pageParam);
    }

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
        /// 可以mybatis plus的配置类自动注入
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return R.ok().message("注册成功");
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public R updateById(@RequestBody User user) {
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        userService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return R.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    @ApiOperation(value = "给用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRelationShip(userId,roleId);
        return R.ok();
    }
}
