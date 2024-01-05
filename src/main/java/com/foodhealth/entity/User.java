package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("tb_user")
@ApiModel("用户的实体类")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("头像")
    private String icon;

    @TableField(exist = false)
    @ApiModelProperty("性别")
    private Integer gender;

    @TableField(exist = false)
    @ApiModelProperty("身高")
    private Double hight;

    @TableField(exist = false)
    @ApiModelProperty("体重")
    private Double weight;
}
