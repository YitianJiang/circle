package com.acircle.circle.service;

import com.acircle.circle.dto.ArticleDetail;

import java.util.List;


public interface EsArticleService {
    /**
     * 根据关键字搜索文章内容
     */
    List<ArticleDetail> search(String keyword, Integer pageNum, Integer pageSize);

}
