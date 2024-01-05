package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "每餐数据的实体类")
public class MealDTO {
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "卡路里")
    private Double calories;
    @ApiModelProperty(notes = "碳水")
    private Double carbohydrate;
    @ApiModelProperty(notes = "维生素")
    private Double vitamin;
    @ApiModelProperty(notes = "蛋白质")
    private Double protein;
    @ApiModelProperty(notes = "脂肪")
    private Double fat;
    @ApiModelProperty(notes = "食物名称")
    private String foodName;
    @ApiModelProperty(notes = "图片")
    private String image;
}
