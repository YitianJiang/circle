package com.acircle.circle.service;

import com.acircle.circle.dto.ArticleAbstract;
import com.acircle.circle.dto.ArticleDetail;
import com.acircle.circle.dto.CreateArticleDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {
    /**
     * 创建文章
     */
    @Transactional
    long create(CreateArticleDto createArticleDto);

    long delete(long id);

    List<ArticleDetail> getHomeRecommendArticles(int pageNum, int pageSize);

    List<ArticleAbstract> getArticleAbstractByArticleId(long articleId);

    List<ArticleDetail> getArticleDetailByArticleId(long articleId);

    List<ArticleDetail> getArticleDetailsByUserId(long userId, int pageNum, int pageSize);

    List<ArticleDetail> getArticleDetailsByCurrentUserId(int pageNum, int pageSize);

    List<ArticleDetail> getUserLikedArticleDetails(int pageNum, int pageSize);

    List<ArticleDetail> getUserBookmarkedArticleDetails(int pageNum, int pageSize);

    List<ArticleDetail> getBrowserHistory(int pageNum, int pageSize);
}
