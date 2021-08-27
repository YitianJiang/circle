package com.acircle.circle.service;

import com.acircle.circle.dto.CommentDetail;
import com.acircle.circle.dto.CommentWithArticleInfo;
import com.acircle.circle.model.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    /**
     * 创建评论
     */
    @Transactional
    long create(Comment comment);

    List<CommentWithArticleInfo> getCommentWithArticleInfosByUserId(int pageNum, int pageSize);

    List<CommentDetail>  getCommentDetailsByArticleId(long articleId, int pageNum, int pageSize);

    long delete(long id);

    long batchDelete(List<Long> commentIds);

    long deleteAll();
}
