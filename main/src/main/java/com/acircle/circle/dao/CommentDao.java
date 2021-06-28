package com.acircle.circle.dao;

import com.acircle.circle.dto.CommentDetail;

import java.util.List;

public interface CommentDao {
    List<CommentDetail> getCommentDetailsByArticleId(long commentId);
}