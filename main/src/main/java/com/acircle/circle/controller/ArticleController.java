package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.dto.ArticleDetail;
import com.acircle.circle.dto.CreateArticleDto;
import com.acircle.circle.service.ArticleService;
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
@Api(tags = "ArticleController", description = "文章管理")
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Reference
    private SnowflakeService snowflakeService;

    @ApiOperation("发布文章")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult CreateArticle(@RequestBody CreateArticleDto createArticleDto) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) return CommonResult.failed();
        createArticleDto.setId(result.getId());
        long createResult = articleService.create(createArticleDto);
        if (createResult > 0) {
            return CommonResult.success(createResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取文章列表")
    @RequestMapping(value = "/getArticles", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getArticleDetails() {
        List<ArticleDetail> articleDetails = articleService.getArticleDetails();
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户发表文章列表")
    @RequestMapping(value = "/getArticles/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getArticlesByUserId(
            @PathVariable long userId,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getArticleDetailsByUserId(userId,pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户收藏过的文章列表")
    @RequestMapping(value = "/getArticles/bookmarked/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getUserBookmarkedArticleDetails(
            @PathVariable long userId,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getUserBookmarkedArticleDetails(userId,pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户评论过的文章列表")
    @RequestMapping(value = "/getArticles/commentted/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getUserCommentedArticleDetails(
            @PathVariable long userId,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getUserCommentedArticleDetails(userId,pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户点赞过的文章列表")
    @RequestMapping(value = "/getArticles/liked/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getUserLikedArticleDetails(
            @PathVariable long userId,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getUserLikedArticleDetails(userId,pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户浏览历史")
    @RequestMapping(value = "/getArticles/browserHistory/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getBrowserHistory(
            @PathVariable long userId,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getBrowserHistory(userId,pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("删除文章")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult DeleteeArticle(@PathVariable long id) {
        long deleteResult = articleService.delete(id);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }
}