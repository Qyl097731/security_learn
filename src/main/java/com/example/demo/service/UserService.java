package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qyl
 * @since 2022-7-09
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
