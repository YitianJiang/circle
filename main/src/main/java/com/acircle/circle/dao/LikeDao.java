package com.acircle.circle.dao;

import com.acircle.circle.dto.LikeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeDao {
    List<LikeDetail> getLikeDetailsByArticleId(@Param("articleId") long articleId, @Param("offSet") int offSet, @Param("limit") int limit);
}
