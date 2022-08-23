package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {


    @Select("select role_name from acl_user u, acl_role r, acl_user_role ur where u.id = #{userId} " +
            "and u.id = ur.user_id and r.id = ur.role_id " +
            "and u.is_deleted = 0 and ur.is_deleted = 0 and r.is_deleted = 0")
    List<String> listRolesByUserId(String userId);
}
