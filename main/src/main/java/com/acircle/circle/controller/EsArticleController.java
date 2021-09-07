package com.acircle.circle.controller;

import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.dto.ArticleDetail;
import com.acircle.circle.service.EsArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
@Api(tags = "EsArticleController", description = "文章搜索")
@RequestMapping("/esArticle")
public class EsArticleController {
    @Autowired
    private EsArticleService esArticleService;


    @ApiOperation(value = "搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ArticleDetail>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        List<ArticleDetail> articleDetails = esArticleService.search(keyword, pageNum, pageSize);
        return CommonResult.success(articleDetails);
    }

    @ApiOperation(value = "获取搜索热榜")
    @RequestMapping(value = "/search/hotspots", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Set<Object>> getSearchHotspots() {
        Set<Object> searchHotSpots = esArticleService.getSearchHotSpots();
        return CommonResult.success(searchHotSpots);
    }


}
