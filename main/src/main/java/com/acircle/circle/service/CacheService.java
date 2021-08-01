package com.acircle.circle.service;

import com.acircle.circle.model.User;


public interface CacheService {
    void setUser(User user);

    User getUser(Long userId);

    void delUser(Long userId);

    void setUserByName(User user);

    User getUserByName(String name);

    void delUserByName(String name);

    void setAuthCode(String telephone, String authCode);

    String getAuthCode(String telephone);
}

