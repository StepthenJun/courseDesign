package com.foodhealth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("每餐数据的实体类")
public class FoodListDTO {
    @ApiModelProperty("早餐")
    List<MealDTO> breakfast = new ArrayList<>();
    @ApiModelProperty("午餐")
    List<MealDTO> lunch = new ArrayList<>();
    @ApiModelProperty("晚餐")
    List<MealDTO> dinner = new ArrayList<>();
}
