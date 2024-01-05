package com.foodhealth.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("tb_user_info")
@ApiModel("用户信息的实体类")
public class UserInfo {

  @ApiModelProperty("用户id")
  private Long userId;

  @ApiModelProperty("性别")
  private Integer gender;

  @ApiModelProperty("用户名称")
  private String username;

/*  @ApiModelProperty("胸围")
  private Double bust;

  @ApiModelProperty("腰围")
  private Double waist;

  @ApiModelProperty("臀围")
  private Double hips;*/

  @ApiModelProperty("身高")
  private Double hight;

  @ApiModelProperty("体重")
  private Double weight;

  @ApiModelProperty("头像？")
  private String icon;

  @TableField(exist = false)
  private Long likesNum;

  @TableField(exist = false)
  private Long collectNum;

  @ApiModelProperty("等级")
  private Long level;

}
