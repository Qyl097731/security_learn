package com.example.demo.handler;


import com.example.demo.utils.R;
import com.example.demo.utils.ResponseUtil;
import com.example.demo.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qyl
 * @program CustomerLogoutHandler.java
 * @Description 退出处理
 * @createTime 2022-08-23 15:55
 */
@Component
public class CustomerLogoutHandler implements LogoutHandler {

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        sessionRepository.deleteById(request.getSession().getId());
        ResponseUtil.out(response, R.ok().message("您的账号已经在其他地方登录,被迫退出").code(ResultCode.UNAUTHORIZED));
    }
}
