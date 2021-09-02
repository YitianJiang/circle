package com.acircle.circle.search.controller;

import com.acircle.circle.common.api.CommonPage;
import com.acircle.circle.common.api.CommonResult;
import com.acircle.circle.search.domain.EsArticle;
import com.acircle.circle.search.service.EsArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "EsArticleController", description = "文章搜索")
@RequestMapping("/esArticle")
public class EsArticleController {
    @Autowired
    private EsArticleService esArticleService;


    @ApiOperation(value = "搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsArticle>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsArticle> esProductPage = esArticleService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

}
