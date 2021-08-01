package com.acircle.circle.dto;

import com.acircle.circle.model.Follow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDetail extends Follow{
    private UserDetail fromUser;
    private UserDetail toUser;
}
