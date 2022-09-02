package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qyl
 * @since 2022-7-09
 */
public interface RoleService extends IService<Role> {
    List<String> listRolesByUserId(String userId);

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(String userId);

    //分配角色
    void saveUserRoleRelationShip(String userId, String[] roleId);
}
