package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.dto.LikeDetail;
import com.acircle.circle.model.Like;
import com.acircle.circle.service.LikeService;
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
@Api(tags = "LikeController", description = "赞管理")
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Reference
    private SnowflakeService snowflakeService;

    @ApiOperation("点赞")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult CreateArticle(@RequestBody Like like) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) return CommonResult.failed();
        like.setId(result.getId());
        long createResult = likeService.create(like);
        if (createResult > 0) {
            return CommonResult.success(createResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("取消赞")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult DeleteLike(@PathVariable long id) {
        long deleteResult = likeService.delete(id);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据文章id分页获取文章的赞")
    @RequestMapping(value = "/get/{articleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult GetCommentDetailsByArticleId(
            @PathVariable long articleId,
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        List<LikeDetail> likeDetails = likeService.getLikeDetailsByArticleId(articleId,pageNum,pageSize);
        return CommonResult.success(likeDetails);
    }
}