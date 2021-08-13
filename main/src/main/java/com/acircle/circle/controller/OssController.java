package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.service.OssService;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/oss")
public class OssController {
    @Autowired
    OssService ossService;

    @ApiOperation(value = "获取阿里云token")
    @RequestMapping(value="/getALiYunOSSToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getALiYunOSSToken(){
        try {
            return CommonResult.success(ossService.assumeRole("session-name"));
        }catch (ClientException e){
            e.printStackTrace();
            return CommonResult.failed();
        }
    }
}
