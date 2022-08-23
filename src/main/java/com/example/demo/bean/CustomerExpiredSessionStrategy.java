package com.example.demo.bean;

import com.example.demo.utils.R;
import com.example.demo.utils.ResponseUtil;
import com.example.demo.utils.ResultCode;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author qyl
 * @program CustomerExpiredSessionStrategy.java
 * @Description 并发登录策略
 * @createTime 2022-08-23 12:08
 */
@Component
public class CustomerExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        ResponseUtil.out(event.getResponse(), R.ok().message("您的账号已经在其他地方登录").code(ResultCode.UNAUTHORIZED));
    }
}