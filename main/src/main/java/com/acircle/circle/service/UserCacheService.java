package com.acircle.circle.service;

import com.acircle.circle.model.User;

/**
 * 后台用户缓存操作类
 */
public interface UserCacheService {
    /**
     * 删除后台用户缓存
     */
    void delUser(Long userId);

    /**
     * 获取缓存后台用户信息
     */
    User getUser(Long userId);
    /**
     * 根据用户名获取缓存后台用户信息
     */
    User getUserByName(String name);
    /**
     * 设置缓存后台用户信息
     */
    void setUser(User user);
    /**
     * 以用户名为key缓存后台用户信息
     */
    void setUserByName(User user);
    /**
     * 设置验证码
     */
    void setAuthCode(String telephone, String authCode);

    /**
     * 获取验证码
     */
    String getAuthCode(String telephone);
}

