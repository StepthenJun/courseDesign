package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录的实体类")
public class LoginFormDTO {
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("密码")
    private String password;
}
