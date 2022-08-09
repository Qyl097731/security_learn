package com.example.demo.filter;

import com.google.common.base.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 访问过滤器
 * </p>
 *
 * @author qyl
 * @since 2022-7-09
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager,RedisTemplate redisTemplate) {
        super(authManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        UsernamePasswordAuthenticationToken authentication = null;
        // 获取redis中的用户信息
        authentication = getAuthentication(req);
        // 已经登录就获取信息 放入安全信息上下文
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        // 从redis中获取权限
        Object object = redisTemplate.opsForValue().get(sessionId);
        if (Objects.nonNull(object)) {
            List<String> permissionValueList = (List<String>)object ;
            Collection<GrantedAuthority> authorities = new ArrayList<>();             // 把权限封装成指定形式的集合
            for(String permissionValue : permissionValueList) {
                if(Strings.isNullOrEmpty(permissionValue)) {
                    continue;
                }
                // SimpleGrantedAuthority 权限内容为String 将String类型的权限存入SimpleGrantedAuthority类
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }
            // 把有用信息塞入UsernamePasswordAuthenticationToken后返回
            // UsernamePasswordAuthenticationToken令牌存了sessionid 和 权限
            return new UsernamePasswordAuthenticationToken(session.getId(),null , authorities);
        }
        return null;
    }
}