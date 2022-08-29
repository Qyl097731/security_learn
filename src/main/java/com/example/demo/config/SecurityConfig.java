package com.example.demo.config;

import com.example.demo.filter.TokenAuthenticationFilter;
import com.example.demo.filter.TokenLoginFilter;
import com.example.demo.security.DefaultPasswordEncoder;
import com.example.demo.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //自定义查询数据库用户名密码和权限信息
    private final UserDetailsService userDetailsService;
    //token 管理工具类（生成 token）
    private final TokenManager tokenManager;
    //redis 操作工具类
    private final RedisTemplate redisTemplate;

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                          TokenManager tokenManager, RedisTemplate redisTemplate, AuthenticationConfiguration authenticationConfiguration) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    /**
     * 新版本配置
     */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //自定义登录页面地址
        http.formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login")               //登录的请求地址
                .successForwardUrl("/session")                 //成功登录之后跳转的地址
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register", "/toLogin").permitAll()                 // 放行哪些请求
                .anyRequest().authenticated()                 // 除了上述请求都进行拦截校验
                .and().userDetailsService(userDetailsService)// 设置后 从数据库查询数据
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))        //
                // 自定义在登录之后存权限
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate))//
                // 自定义验证过滤器 session是否存在
                .httpBasic();
        return http.build();
    }

    @Bean
    /**
     * BCryptPasswordEncoder 官方推荐的密码加密规则
     * 采用SHA-256 +随机盐+密钥对明文密码进行加密。SHA系列是Hash算法，
     * 不是加密算法，使用加密算法意味着可以解密（这个与编码/解码一样），但是采用Hash处理，其过程是不可逆的。
     * 每次密码加密结果都不一致，且不存在解密
     * 用了SecureRandom 是对 Random的一种扩展
     * SecureRandom类收集了一些随机事件，比如鼠标点击，键盘点击等等，SecureRandom 使用这些随机事件作为种子 所以种子不可预测
     * Random 默认使用系统时间的毫秒数作为种子 有规律可循
     */
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    /**
     *  给静态资源放行
     *  Spring Security 给一个地址放行，有两种方式：
     *  1. 被放行的资源，不需要经过 Spring Security 过滤器链（静态资源一般使用这种）。
     *  2. 经过 Spring Security 过滤器链，但是不拦截（如果是一个接口想要匿名访问，一般使用这种 SecurityFilterChain中放行的接口）。
     *  很明显下面这种方形方式是第一种
     */
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 哪些web请求可以直接放行 不需要拦截
        return (web) -> web.ignoring().antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/**", "/api/**", "/register.html", "/login.html");
    }

}