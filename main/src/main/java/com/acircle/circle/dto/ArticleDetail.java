package com.acircle.circle.dto;

import com.acircle.circle.model.Article;
import com.acircle.circle.model.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleDetail extends Article{
    private List<Image> images;
    private List<LikeDetail> likeDetails;
    private List<CommentDetail> commentDetails;
    private UserDetail userDetail;
}
