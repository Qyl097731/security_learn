package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Permission;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

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
}
