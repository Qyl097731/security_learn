package com.example.demo.config;

import com.example.demo.utils.R;
import com.example.demo.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author qyl
 * @program CustomExpiredSessionStrategy.java
 * @Description TODO
 * @createTime 2022-07-14 15:10
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        ResponseUtil.out(event.getResponse(), R.ok().message("expired……"));
    }
}
