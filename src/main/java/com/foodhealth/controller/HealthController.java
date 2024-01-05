package com.foodhealth.controller;

import com.foodhealth.dto.Result;
import com.foodhealth.service.HealthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping()
@Api(tags ="食物数据相关")
public class HealthController {
    @Autowired
    private HealthService healthService;

    @ApiOperation("FoodList界面返回指定日期吃的食物")
    @GetMapping("foodlist/getDataByDate/{date}")
    public Result getDataByDate(@PathVariable  String date){
        LocalDate localDate = LocalDate.parse(date);
        return healthService.getDataByDate(localDate);
    }

    @ApiOperation("FoodList界面搜索数据")
    @GetMapping("foodlist/search")
    public Result getDataByCondition(@RequestParam String keyWord){
        keyWord = keyWord.toLowerCase();
        return healthService.getDataByCondition(keyWord);
    }

    @ApiOperation("Home界面返回指定日期的营养数据")
    @GetMapping("home/getNutrition")
    public Result getNutrition(@RequestParam  String date){
        LocalDate localDate = LocalDate.parse(date);
        log.info("时间是：{}"+localDate);
        return healthService.getNutrition(localDate);
    }

    @ApiOperation("Home界面返回当天的数据")
    @GetMapping("home/getData")
    public Result getTodayData(){
        LocalDate localDate = LocalDate.now();
        return healthService.getTodayData(localDate);
    }
}
