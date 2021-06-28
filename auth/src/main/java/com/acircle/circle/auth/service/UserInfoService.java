package com.acircle.circle.auth.service;

import com.acircle.circle.common.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("circle-main")
public interface UserInfoService {

    @GetMapping("/user/loadByUsername")
    UserDto loadUserByUsername(@RequestParam("username") String username);
}
