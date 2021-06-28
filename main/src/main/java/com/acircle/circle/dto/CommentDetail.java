package com.acircle.circle.dto;

import com.acircle.circle.model.Comment;
import com.acircle.circle.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDetail extends Comment {
    private User fromUser;
    private User toUser;
}
