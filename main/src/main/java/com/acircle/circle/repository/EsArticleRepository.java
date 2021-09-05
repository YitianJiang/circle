package com.acircle.circle.repository;

import com.acircle.circle.dto.EsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 搜索商品ES操作类
 */
public interface EsArticleRepository extends ElasticsearchRepository<EsArticle, Long> {
    /**
     * 搜索查询
     *
     * @param keywords          文章关键字
     * @param page              分页信息
     */
    Page<EsArticle> findByText(String keywords, Pageable page);

}
