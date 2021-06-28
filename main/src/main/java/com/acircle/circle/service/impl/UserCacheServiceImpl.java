package com.acircle.circle.service.impl;

import com.acircle.circle.common.annotation.CacheException;
import com.acircle.circle.common.service.RedisService;
import com.acircle.circle.model.User;
import com.acircle.circle.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;

    @Override
    public void delUser(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + userId;
        redisService.del(key);
    }

    @Override
    public User getUser(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + userId;
        return (User) redisService.get(key);
    }

    @Override
    public User getUserByName(String name) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + name;
        return (User) redisService.get(key);
    }

    @Override
    public void setUser(User user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + user.getId();
        redisService.set(key, user, REDIS_EXPIRE);
    }

    @Override
    public void setUserByName(User user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + user.getName();
        redisService.set(key, user, REDIS_EXPIRE);
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
