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

    @ApiOperation("首页获取文章列表")
    @RequestMapping(value = "/getHomeRecommendArticles", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getHomeRecommendArticles(
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getHomeRecommendArticles(pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据文章id获取文章详情")
    @RequestMapping(value = "/getArticleDetailByArticleId/{articleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getArticleDetailByArticleId(@PathVariable long articleId){
        List<ArticleDetail> articleDetails = articleService.getArticleDetailByArticleId(articleId);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户发表文章列表")
    @RequestMapping(value = "/getArticlesByUserId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getArticlesByUserId(
            @PathVariable long userId,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getArticleDetailsByUserId(userId,pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("获取当前用户发表文章列表")
    @RequestMapping(value = "/getArticlesByCurrentUserId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getArticlesByCurrentUserId(
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getArticleDetailsByCurrentUserId(pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户收藏过的文章列表")
    @RequestMapping(value = "/getArticles/bookmarked", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getUserBookmarkedArticleDetails(
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getUserBookmarkedArticleDetails(pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户点赞过的文章列表")
    @RequestMapping(value = "/getArticles/liked", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getUserLikedArticleDetails(
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getUserLikedArticleDetails(pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("根据用户id获取用户浏览历史")
    @RequestMapping(value = "/getArticles/browserHistory", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> getBrowserHistory(
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleDetail> articleDetails = articleService.getBrowserHistory(pageNum,pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation("删除文章")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult DeleteArticle(@PathVariable long id) {
        long deleteResult = articleService.delete(id);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }
}