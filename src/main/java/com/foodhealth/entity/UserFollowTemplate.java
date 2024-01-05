package com.foodhealth.entity;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
* 
* @TableName tb_user_follow_template
*/
@Data
@Accessors(chain = true)
@TableName("tb_user_follow_template")
@ApiModel("点赞收藏模板")
public class UserFollowTemplate implements Serializable {

    /**
    * 
    */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("")
    private Long id;
    /**
    * 模板id
    */
    @ApiModelProperty("模板id")
    private Long templateId;
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
    * 是否收藏
    */
    @ApiModelProperty("是否收藏")
    private Boolean isCollect;
    /**
    * 是否点赞
    */
    @ApiModelProperty("是否点赞")
    private Boolean isLike;



}
