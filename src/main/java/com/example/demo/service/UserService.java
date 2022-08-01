package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import com.example.demo.utils.R;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qyl
 * @since 2022-7-09
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查用户
     * @param username
     * @return
     */
    User selectByUsername(String username);
}
