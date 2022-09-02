package com.example.demo.controller.role;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import com.example.demo.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author qyl
 * @program RoleController.java
 * @Description 角色控制
 * @createTime 2022-09-02 15:00
 */
@RestController
@RequestMapping("/nsec/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("all/{page}/{limit}")
    public R all(
            @ApiParam(name = "page", value = "当前页码", required = false,defaultValue = "0")
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = false,defaultValue = "10")
            @PathVariable Long limit){
        Page<Role> pageParam = new Page<>(page, limit);
        roleService.page(pageParam, null);
        return R.ok().data("page",pageParam);
    }

    @ApiOperation(value = "获取角色信息")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return R.ok().data("role", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public R save(@RequestBody Role role) {
        roleService.save(role);
        return R.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public R updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }

    @ApiOperation(value = "删除角色，应该逻辑删除、这里偷懒了")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        roleService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return R.ok();
    }
}
