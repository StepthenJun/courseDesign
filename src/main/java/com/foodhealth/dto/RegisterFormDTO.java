package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("注册的实体类")
public class RegisterFormDTO {
    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;
}
