package com.acircle.circle.search.service;

import com.acircle.circle.search.domain.EsArticle;
import org.springframework.data.domain.Page;

public interface EsArticleService {
    /**
     * 根据关键字搜索文章内容
     */
    Page<EsArticle> search(String keyword, Integer pageNum, Integer pageSize);

}
