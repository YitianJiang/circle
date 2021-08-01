package com.acircle.circle.dto;

import com.acircle.circle.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetail extends User {
   private long fansNum;
}
