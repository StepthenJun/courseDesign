package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("注册的实体类")
public class RegisterFormDTO {

    private String phone;

    private String code;
}
