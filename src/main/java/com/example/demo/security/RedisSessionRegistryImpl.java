package com.example.demo.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author qyl
 * @program RedisSessionRegistryImpl.java
 * @Description TODO
 * @createTime 2022-07-14 10:45
 */
public class RedisSessionRegistryImpl implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {

    private RedisTemplate redisTemplate;
    //维护所有sessionId列表的zset score为过期时间
    private final static String SESSION_LIST_KEY = "session:list";
    //维护指定用户sessionId列表的zset score为过期时间
    private final static String SESSION_USER_LIST_KEY = "session:list:userId:%s";
    //存储sessionId对应的数据
    private final static String SESSION_ITEM_KEY = "session:item:%s";

    public RedisSessionRegistryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {
        String sessionId = sessionDestroyedEvent.getId();
        this.removeSessionInformation(sessionId);
    }

    /**
     * 获得所有会话
     *
     * @return
     */
    @Override
    public List<Object> getAllPrincipals() {
        Set<Object> sessionIds = redisTemplate.boundZSetOps(SESSION_LIST_KEY).range(0, -1);
        if (CollectionUtils.isEmpty(sessionIds)) {
            return null;
        }
        return new ArrayList<>(sessionIds);
    }

    /**
     * 获得指定用户列表的会话
     *
     * @param includeExpiredSessions
     * @return
     */
    @Override
    public List<SessionInformation> getAllSessions(Object userId, boolean includeExpiredSessions) {
        Set<String> sessionsUsedByPrincipal = redisTemplate.boundZSetOps(String.format(SESSION_USER_LIST_KEY, userId)).range(0, -1);
        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        } else {
            List<SessionInformation> list = new ArrayList(sessionsUsedByPrincipal.size());
            Iterator var5 = sessionsUsedByPrincipal.iterator();

            while (true) {
                SessionInformation sessionInformation;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return list;
                        }

                        String sessionId = (String) var5.next();
                        sessionInformation = this.getSessionInformation(sessionId);
                    } while (sessionInformation == null);
                } while (!includeExpiredSessions && sessionInformation.isExpired());

                list.add(sessionInformation);
            }
        }
    }

    /**
     * 根据sessionId获得会话
     *
     * @param sessionId
     * @return
     */
    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        Object jsonValue = redisTemplate.opsForValue().get(String.format(SESSION_ITEM_KEY, sessionId));
        if (StringUtils.isEmpty(jsonValue)) {
            return null;
        }
        JSONObject jsonObject= JSON.parseObject(jsonValue.toString());
        SessionInformation sessionInformation=new  SessionInformation(JSON.parseObject(jsonObject.getString("principal"), User.class),jsonObject.getString("sessionId"),jsonObject.getDate("lastRequest"));
        if(jsonObject.getBoolean("expired")){
            sessionInformation.expireNow();
        }
        return sessionInformation;
    }

    /**
     * 刷新session过期时间
     *
     * @param sessionId
     */
    @Override
    public void refreshLastRequest(String sessionId) {
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
        }
        registerNewSession(sessionId, info);
    }

    /**
     * 注册session
     *
     * @param sessionId
     * @param principal
     */
    @Override
    public void registerNewSession(String sessionId, Object principal) {
        SessionInformation sessionInformation =new SessionInformation(principal, sessionId, new Date());
        redisTemplate.opsForValue().set(String.format(SESSION_ITEM_KEY, sessionId), JSON.toJSONString(sessionInformation));
        redisTemplate.boundZSetOps(SESSION_LIST_KEY).add(sessionId, System.currentTimeMillis() + 30000);
        String username;
        //因为第一次传入的是username 登录成功传入的是我们的UserDetails对象
        if(principal instanceof UserDetails){
            username=((UserDetails)principal).getUsername();
        }else{
            username=principal.toString();
        }

        redisTemplate.boundZSetOps(String.format(SESSION_ITEM_KEY, username)).add(sessionId, System.currentTimeMillis() + 30000);

    }

    /**
     * 从会话中移除
     *
     * @param sessionId
     */
    @Override
    public void removeSessionInformation(String sessionId) {
        SessionInformation sessionInformation = getSessionInformation(sessionId);
        redisTemplate.delete(String.format(SESSION_ITEM_KEY, sessionId));
        redisTemplate.boundZSetOps(SESSION_LIST_KEY).add(sessionId, System.currentTimeMillis() + 30000);
        if (sessionInformation != null) {
            redisTemplate.boundZSetOps(String.format(SESSION_ITEM_KEY, sessionInformation.getPrincipal())).add(sessionId, System.currentTimeMillis() + 30000);
        }
    }
}