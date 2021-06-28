package com.acircle.circle.service;

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

    List<ArticleDetail> getArticleDetails();

    List<ArticleDetail> getArticleDetailsByUserId(long userId, int pageNum, int pageSize);

    List<ArticleDetail> getUserLikedArticleDetails(long userId, int pageNum, int pageSize);

    List<ArticleDetail> getUserCommentedArticleDetails(long userId, int pageNum, int pageSize);

    List<ArticleDetail> getUserBookmarkedArticleDetails(long userId, int pageNum, int pageSize);

    List<ArticleDetail> getBrowserHistory(long userId, int pageNum, int pageSize);
}
