package com.foodhealth.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Health;

import java.time.LocalDate;
import java.util.Date;

public interface HealthService extends IService<Health> {
    Result uploadMeal(JSONObject result,String foodName,String imgurl);

    Result getDataByDate(LocalDate date);

    Result getNutrition(LocalDate localDate);

    Result getTodayData(LocalDate localDate);

    Result getDataByCondition(String keyWord);
}
