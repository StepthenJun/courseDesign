package com.foodhealth.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.constants.SystemConstants;
import com.foodhealth.constants.TimeConstants;
import com.foodhealth.dto.FoodListDTO;
import com.foodhealth.dto.MealDTO;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.Health;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.mapper.HealthMapper;
import com.foodhealth.service.HealthService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {

    @Override
    public Result uploadMeal(JSONObject result,String foodName,String imgurl) {
        UserDTO user = UserHolder.getUser();
        Health userHealth = new Health();

        Long userId = user.getId();
        Double calories = roundToTwoDecimalPlaces(parseDoubleFromString(result.getStr("calories")));
        Double carbohydrate = roundToTwoDecimalPlaces(parseDoubleFromString(result.getStr("carbohydrate")));
        Double vitamin = roundToTwoDecimalPlaces(parseDoubleFromString(result.getStr("vitamin")));
        Double protein = roundToTwoDecimalPlaces(parseDoubleFromString(result.getStr("protein")));
        Double fat = roundToTwoDecimalPlaces(parseDoubleFromString(result.getStr("fat")));

        userHealth.setUserId(userId)
                .setFat(fat)
                .setCalories(calories)
                .setCarbohydrate(carbohydrate)
                .setProtein(protein)
                .setVitamin(vitamin)
                .setFoodName(foodName)
                .setImage(imgurl);
        boolean save = save(userHealth);
        if (save){
            return Result.ok("上传成功");
        }
        return Result.fail("上传失败");
    }

    @Override
    public Result getDataByDate(LocalDate date) {
        FoodListDTO foodList = new FoodListDTO();
        List<MealDTO> matchingDates = getMealDTOS(date);
        for (MealDTO matchingDate : matchingDates) {
            LocalDateTime mealTime = matchingDate.getCreateTime();
            if (mealTime.isBefore(TimeConstants.BREAK_FAST_TIME)){
                foodList.getBreakfast().add(matchingDate);
            }
            else if (mealTime.isBefore(TimeConstants.LUNCH_TIME)&& mealTime.isAfter(TimeConstants.BREAK_FAST_TIME)){
                foodList.getLunch().add(matchingDate);
            }
            else {
                foodList.getDinner().add(matchingDate);
            }
        }
        return Result.ok(foodList);
    }

    @Override
    public Result getNutrition(LocalDate localDate) {
        List<MealDTO> mealDTOS = getMealDTOS(localDate);
        Map<String, Double> stats = new HashMap<>();
        // 初始化统计结果
        stats.put("calories", 0.0);
        stats.put("carbohydrate", 0.0);
        stats.put("vitamin", 0.0);
        stats.put("protein", 0.0);
        stats.put("fat", 0.0);

        // 遍历 mealDTOS 列表并进行累加
        for (MealDTO mealDTO : mealDTOS) {
            stats.put("calories", stats.get("calories") + mealDTO.getCalories());
            stats.put("carbohydrate", stats.get("carbohydrate") + mealDTO.getCarbohydrate());
            stats.put("vitamin", stats.get("vitamin") + mealDTO.getVitamin());
            stats.put("protein", stats.get("protein") + mealDTO.getProtein());
            stats.put("fat", stats.get("fat") + mealDTO.getFat());
        }

        return Result.ok(stats);
    }

    @Override
    public Result getTodayData(LocalDate localDate) {
        Map<String,String> data = new HashMap<>();
        String username = UserHolder.getUser().getUsername();
//        获取当天的食物数据
        List<MealDTO> mealDTOS = getMealDTOS(localDate);
        data.put("username",username);
        if (mealDTOS != null) {
//        获取最后一餐
            MealDTO mealDTO = mealDTOS.get(mealDTOS.size() - 1);
            String foodName = mealDTO.getFoodName();
            data.put("lastMedal", foodName);
            Double calories = 0.0;
            for (MealDTO dto : mealDTOS) {
                calories += dto.getCalories();
            }
            data.put("caloricIntake",""+roundToTwoDecimalPlaces(calories));

            return Result.ok(data);
        }
        else return Result.ok("No Data");
    }

    @Override
    public Result getDataByCondition(String keyWord) {
        Long id = UserHolder.getUser().getId();
        List<Health> healthList = query()
                .eq("user_id",id)
                .like("LOWER(food_name)", keyWord)
                .list();
        return Result.ok(healthList);
    }


    private List<MealDTO> getMealDTOS(LocalDate date) {
        Long id = UserHolder.getUser().getId();

        List<Health> healthList = query().eq("user_id", id).list();

        List<MealDTO> matchingDates = new ArrayList<>();

        for (Health health : healthList) {
            LocalDateTime timestamp = health.getCreateTime(); // 假设 timestamp 是 LocalDateTime 类型字段
            LocalDate localDate = timestamp.toLocalDate();
            if (localDate.isEqual(date)) {
                matchingDates.add(BeanUtil.copyProperties(health,MealDTO.class));
            }
        }
        return matchingDates;
    }



    private Double parseDoubleFromString(String value) {
        if (StrUtil.isBlank(value)) {
            return 0.0;
        }
        String numberOnly = value.replaceAll("[^\\d.]+", "");
        return Double.parseDouble(numberOnly);
    }

    private Double roundToTwoDecimalPlaces(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
