package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.common.domain.UserDto;
import com.acircle.circle.dto.UserCreateDto;
import com.acircle.circle.model.User;
import com.acircle.circle.service.FollowService;
import com.acircle.circle.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sankuai.inf.leaf.api.SnowflakeService;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(tags = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Reference
    private SnowflakeService snowflakeService;

    @ApiOperation("注册")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult CreateUser(@RequestBody UserCreateDto userCreateDto) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) return CommonResult.failed();
        userCreateDto.setId(result.getId());
        long createResult = userService.create(userCreateDto);
        if (createResult > 0) {
            return CommonResult.success(createResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    @ResponseBody
    public UserDto loadUserInfo(@RequestParam String username) { ;
        return userService.loadUserByUsername(username);
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult Login(@RequestBody User user) {
        return userService.login(user);
    }

    @ApiOperation("测试登录状态")
    @RequestMapping(value = "/testLogin", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult Login() {
        return CommonResult.success("已登录");
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getCurrentUser() {
        User user =  userService.getCurrentUser();
        user.setPassword("");
        return CommonResult.success(user);
    }

    @ApiOperation("更新用户信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult DeleteUser(@RequestBody User user) {
        long updateResult = userService.update(user);
        if (updateResult > 0) {
            return CommonResult.success(updateResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("注销")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult DeleteUser(@PathVariable long id) {
        long deleteResult = userService.delete(id);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }
}