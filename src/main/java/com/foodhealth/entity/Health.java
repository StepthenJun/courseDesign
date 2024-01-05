package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("tb_health")
public class Health {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty()
    private LocalDateTime createTime;
    @ApiModelProperty("卡路里")
    private Double calories;
    @ApiModelProperty("碳水")
    private Double carbohydrate;
    @ApiModelProperty("维生素")
    private Double vitamin;
    @ApiModelProperty("蛋白质")
    private Double protein;
    @ApiModelProperty("脂肪")
    private Double fat;
    @ApiModelProperty("食物名称")
    private String foodName;
    @ApiModelProperty("图片")
    private String image;
}
