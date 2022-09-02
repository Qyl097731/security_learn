package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author qyl
 * @since 2022-7-09
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户id获取用户菜单
     *
     * @param id
     * @return List<JSONObject>
     */
    List<String> selectPermissionValueByUserId(String id);

    List<Permission> queryAllMenu();

    void removeChildById(String id);

    void saveRolePermissionRelationShip(String roleId, String[] permissionId);

    List<Permission> listByRId(String roleId);


    List<JSONObject> selectPermissionByUserId(String id);
}
