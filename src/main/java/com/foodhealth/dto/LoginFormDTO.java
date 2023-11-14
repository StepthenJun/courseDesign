package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("登录的实体类")
public class LoginFormDTO {
    private String phone;
    private String password;
}
