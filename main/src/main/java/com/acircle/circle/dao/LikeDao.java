package com.acircle.circle.dao;

import com.acircle.circle.dto.LikeDetail;

import java.util.List;

public interface LikeDao {
    List<LikeDetail> getLikeDetailsByArticleId(long articleId);
}
