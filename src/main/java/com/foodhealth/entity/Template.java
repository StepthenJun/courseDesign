package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_template")
@ApiModel("模板的实体类")
public class Template {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("食物名称")
    private String foodName;

    @ApiModelProperty("图片地址")
    private String img;

    @TableField(exist = false)
    private Boolean isColloct;

    @TableField(exist = false)
    private Boolean isLike;

    @ApiModelProperty("点赞数量")
    private Long likedNum;

    @ApiModelProperty("收藏数量")
    private Long collectedNum;

    @ApiModelProperty("卡路里")
    private Double calories;

}
