package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Permission;

import java.util.List;


/**
 * @author nsec
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id获取权限列表  权限 ：  role.permisson 故 已经起到了区分角色的作用
     * @param id
     * @return
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 查询所有的权限
     * @return List<String>
     */
    List<String> selectAllPermissionValue();

    /**
     * 根据用户id返回用户权限
     * @param userId
     * @return List<Permission>
     */
    List<Permission> selectPermissionByUserId(String userId);
}
