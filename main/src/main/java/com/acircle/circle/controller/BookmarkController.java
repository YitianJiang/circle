package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.model.Bookmark;
import com.acircle.circle.service.BookMarkService;
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
@Api(tags = "BookmarkController", description = "收藏管理")
@RequestMapping("/article/bookmark")
public class BookmarkController {
    @Autowired
    private BookMarkService bookMarkService;

    @Reference
    private SnowflakeService snowflakeService;

    @ApiOperation("收藏")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult CreateArticle(@RequestBody Bookmark bookmark) {
        Result result = snowflakeService.getId("normal");
        if(result.getStatus() == Status.EXCEPTION) return CommonResult.failed();
        bookmark.setId(result.getId());
        long createResult = bookMarkService.create(bookmark);
        if (createResult > 0) {
            return CommonResult.success(createResult);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("取消收藏")
    @RequestMapping(value = "/delete/{bookmarkId}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult DeleteLike(@PathVariable long bookmarkId) {
        long deleteResult = bookMarkService.delete(bookmarkId);
        if (deleteResult > 0) {
            return CommonResult.success(deleteResult);
        } else {
            return CommonResult.failed();
        }
    }
}