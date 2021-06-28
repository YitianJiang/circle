package com.acircle.circle.service;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.common.domain.UserDto;
import com.acircle.circle.dto.UpdatePasswordParam;
import com.acircle.circle.dto.UserCreateDto;
import com.acircle.circle.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    long create(UserCreateDto userCreateDto);

    CommonResult login(User user);

    User getCurrentUser();

    UserDto loadUserByUsername(String username);

    long update(User user);

    int updatePassword(UpdatePasswordParam param);

    long delete(long id);
}
