package com.acircle.circle.dto;

import com.acircle.circle.model.Article;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleDto extends Article {
        private String[] imageUrls;
}
