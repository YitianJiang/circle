package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.dto.CommentWithArticleInfo;
import com.acircle.circle.model.Comment;
import com.acircle.circle.service.CommentService;
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
@Api(tags = "CommentController", description = "评论管理")
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Reference
    private SnowflakeService snowflakeService;

    @ApiOperation("发布评论")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult Create(@RequestBody Comment comment) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) return CommonResult.failed();
        comment.setId(result.getId());
        long createResult = commentService.create(comment);
        if (createResult > 0) {
            return CommonResult.success(createResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据用户id获取用户评论列表")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult GetCommentsByUserId(
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<CommentWithArticleInfo> commentWithArticleInfos = commentService.getCommentWithArticleInfosByUserId(pageNum,pageSize);
        return CommonResult.success(commentWithArticleInfos);
    }

    @ApiOperation("删除评论")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult Delete(@PathVariable long id) {
        long deleteResult = commentService.delete(id);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }
}