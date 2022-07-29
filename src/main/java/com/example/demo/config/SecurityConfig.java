//package com.example.demo.config;
//
//import com.example.demo.security.DefaultPasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * <p>
// * Security配置类
// * </p>
// *
// * @author qyl
// * @since 2022-7-09
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)      //prePostEnabled = true 解锁 @PreAuthority 和 @PostAuthority，以此可以实现权限的校验
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private DefaultPasswordEncoder defaultPasswordEncoder;
//
//    /**
//     * 配置设置
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").successForwardUrl ("/user/manage")
//                .permitAll()
//                .and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/login","/register").permitAll()
//                .anyRequest().authenticated()
//                .and().userDetailsService(userDetailsService)
//                .httpBasic();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    /**
//     * 密码处理
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //设置自己定义的userDetailsService来实现去数据库查询账号密码，来进行信息比对
//        //推荐BCryptPasswordEncoder()
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//
//
//    /**
//     * 配置哪些请求不拦截
//     *
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // 哪些web请求可以直接放行 不需要拦截
//        web.ignoring().antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**", "/api/**", "/register.html","/login.html");
//    }
//
//}