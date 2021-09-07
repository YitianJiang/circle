package com.acircle.circle.service;

import com.acircle.circle.dto.ArticleDetail;

import java.util.List;
import java.util.Set;


public interface EsArticleService {
    /**
     * 根据关键字搜索文章内容
     */
    List<ArticleDetail> search(String keyword, Integer pageNum, Integer pageSize);
    /**
     * 获取搜索热榜
     */
    Set<Object> getSearchHotSpots() ;

}
