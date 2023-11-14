package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("用户界面数据的实体类")
public class UserDTO {
    private Long id;
    private String username;
    private String icon;

}
