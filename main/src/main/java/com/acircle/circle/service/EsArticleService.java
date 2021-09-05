package com.acircle.circle.service;

import com.acircle.circle.dto.EsArticle;
import org.springframework.data.domain.Page;


public interface EsArticleService {
    /**
     * 根据关键字搜索文章内容
     */
    Page<EsArticle> search(String keyword, Integer pageNum, Integer pageSize);

}
