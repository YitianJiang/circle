package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.dto.FollowDetail;
import com.acircle.circle.model.Follow;
import com.acircle.circle.service.FollowService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sankuai.inf.leaf.api.SnowflakeService;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "FollowController", description = "关注管理")
@RequestMapping("/user/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @Reference
    private SnowflakeService snowflakeService;

    @ApiOperation("关注")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult FollwUser(@RequestBody Follow follow) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) return CommonResult.failed();
        follow.setId(result.getId());
        long createResult = followService.create(follow);
        if (createResult > 0) {
            return CommonResult.success(createResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("取消关注")
    @RequestMapping(value = "/delete/{followId}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult CancelFollowUser(@PathVariable long followId) {
        long deleteResult = followService.delete(followId);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("我的关注")
    @RequestMapping(value = "/peopleIFollow", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getFollowedPeopleByUserId() {
        List<FollowDetail> peopleIFollow  = followService.getPeopleIFollow();
        return CommonResult.success(peopleIFollow);
    }

    @ApiOperation("我的粉丝")
    @RequestMapping(value = "/myFans", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getFansByUserId() {
        List<FollowDetail> myFans = followService.getMyFans();
        return CommonResult.success(myFans);
    }
}