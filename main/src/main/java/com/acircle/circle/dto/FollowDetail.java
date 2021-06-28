package com.acircle.circle.dto;

import com.acircle.circle.model.Follow;
import com.acircle.circle.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDetail extends Follow{
    private User fromUser;
    private User toUser;
}
