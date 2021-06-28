package com.acircle.circle.dto;

import com.acircle.circle.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class LoginReturnDto {
    LinkedHashMap tokenDetail;
    User userDetail;
}
