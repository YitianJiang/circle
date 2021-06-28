package com.acircle.circle.service;

import com.acircle.circle.model.Like;
import org.springframework.transaction.annotation.Transactional;

public interface LikeService {
    /**
     * 创建赞
     */
    @Transactional
    long create(Like like);

    long delete(long id);

}