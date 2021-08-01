package com.acircle.circle.dto;

import com.acircle.circle.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWithArticleInfo extends Comment {
    ArticleAbstract articleAbstract;
}
