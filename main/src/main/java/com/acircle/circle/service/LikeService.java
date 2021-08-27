package com.acircle.circle.service;

import com.acircle.circle.dto.LikeDetail;
import com.acircle.circle.model.Like;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikeService {
    /**
     * 创建赞
     */
    @Transactional
    long create(Like like);

    List<LikeDetail> getLikeDetailsByArticleId(long articleId, int pageNum, int pageSize);

    long delete(long id);

}