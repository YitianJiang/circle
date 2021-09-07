package com.acircle.circle.service.impl;

import com.acircle.circle.common.annotation.CacheException;
import com.acircle.circle.common.service.RedisService;
import com.acircle.circle.model.User;
import com.acircle.circle.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.user}")
    private String REDIS_KEY_USER;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;

    @Override
    public void setUser(User user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + user.getId();
        redisService.set(key, user, REDIS_EXPIRE);
    }

    @Override
    public User getUser(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + userId;
        return (User) redisService.get(key);
    }

    @Override
    public void delUser(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + userId;
        redisService.del(key);
    }

    @Override
    public void setUserByName(User user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + user.getName();
        redisService.set(key, user, REDIS_EXPIRE);
    }

    @Override
    public User getUserByName(String name) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + name;
        return (User) redisService.get(key);
    }

    @Override
    public void delUserByName(String name) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + name;
        redisService.del(key);
    }

    @CacheException
    @Override
    public void setAuthCode(String telephone, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }

    @CacheException
    @Override
    public String getAuthCode(String telephone) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
        return (String) redisService.get(key);
    }


}
