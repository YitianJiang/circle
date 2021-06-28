package com.acircle.circle.dao;

import com.acircle.circle.dto.UserDetail;

public interface UserDao {
    UserDetail getUserDetailsByUserId(long userId);
}
