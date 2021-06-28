package com.acircle.circle.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserCreateDto {
    @NotEmpty
    @ApiModelProperty(value = "用户id", required = true)
    private long id;
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String name;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}

