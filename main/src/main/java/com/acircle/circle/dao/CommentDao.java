package com.acircle.circle.dao;

import com.acircle.circle.dto.CommentDetail;
import com.acircle.circle.dto.CommentWithArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    List<CommentDetail> getCommentDetailsByArticleId(long commentId);
    List<CommentWithArticleInfo> getComments(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
}