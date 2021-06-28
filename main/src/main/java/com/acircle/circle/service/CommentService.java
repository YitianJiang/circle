package com.acircle.circle.service;

import com.acircle.circle.model.Comment;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    /**
     * 创建评论
     */
    @Transactional
    long create(Comment comment);

    long delete(long id);

}
