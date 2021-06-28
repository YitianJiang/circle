package com.acircle.circle.dto;

import com.acircle.circle.model.Like;
import com.acircle.circle.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDetail extends Like{
    private User user;
}
