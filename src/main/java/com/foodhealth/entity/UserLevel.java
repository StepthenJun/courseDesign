package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_user_level")
@ApiModel("用户浏览社区文章的记录表")
public class UserLevel {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userID;

    private Long communityTypeId;

    private Long score;

    private Long publishNumber;
}
