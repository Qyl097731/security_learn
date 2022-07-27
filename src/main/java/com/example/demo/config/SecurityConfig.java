package com.example.demo.config;

import com.example.demo.filter.TokenAuthenticationFilter;
import com.example.demo.filter.TokenLoginFilter;
import com.example.demo.security.DefaultPasswordEncoder;
import com.example.demo.security.TokenLogoutHandler;
import com.example.demo.security.TokenManager;
import com.example.demo.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * Security配置类
 * </p>
 *
 * @author qyl
 * @since 2022-7-09
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)      //prePostEnabled = true 解锁 @PreAuthority 和 @PostAuthority，以此可以实现权限的校验
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义查询数据库用户名密码和权限信息
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 配置设置
     *
     * @param http
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()                      // 关闭跨站请求伪造
//                .authorizeRequests()
//                .antMatchers("/login","/toLogin","/register").permitAll()   //与login toLogin匹配的可以放行
//                .anyRequest().authenticated();               // 所有的请求都要进行验证
//    }

    /**
     * 密码处理
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder());
    }


    /**
     * 注入密码的处理器
     */
    @Bean
    public PasswordEncoder BCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置哪些请求不拦截
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 哪些web请求可以直接放行 不需要拦截
        web.ignoring().antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**", "/api/**");
    }
}