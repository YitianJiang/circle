package com.acircle.circle.dao;

import com.acircle.circle.dto.ArticleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    List<ArticleDetail> getArticleBaseInfo();
    List<ArticleDetail> getArticleDetailsByUserId(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getUserLikedArticleDetails(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getUserCommentedArticleDetails(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getUserBookmarkedArticleDetails(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getBrowserHistory(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
}
