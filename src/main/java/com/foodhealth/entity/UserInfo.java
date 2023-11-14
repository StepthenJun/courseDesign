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
@ApiModel("用户数据的实体类")
public class UserInfo {

  @ApiModelProperty()
  private Long userId;

  @ApiModelProperty()
  private Integer gender;

  @ApiModelProperty()
  private String username;

  @ApiModelProperty()
  private Double bust;

  @ApiModelProperty()
  private Double waist;

  @ApiModelProperty()
  private Double hips;

  @ApiModelProperty()
  private Double hight;

  @ApiModelProperty()
  private LocalDateTime createTime;

  @ApiModelProperty()
  private LocalDateTime updateTime;

  @ApiModelProperty()
  private String icon;




}
