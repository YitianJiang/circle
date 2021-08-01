package com.acircle.circle.dao;

import com.acircle.circle.dto.ArticleAbstract;
import com.acircle.circle.dto.ArticleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    List<ArticleDetail> getHomeRecommendArticlesBaseInfo( @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleAbstract> getArticleAbstractByArticleId(@Param("articleId") long articleId);
    List<ArticleDetail> getArticleBaseInfoByArticleId(@Param("articleId") long articleId);
    List<ArticleDetail> getArticleDetailsByUserId(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getUserLikedArticleDetails(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getUserBookmarkedArticleDetails(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    List<ArticleDetail> getBrowserHistory(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
}
