package com.foodhealth.api;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@Component
public class FoodNutritionAPI {

    @Value("${api.edamam.base_url}")
    private String BASE_URL;

    @Value("${api.edamam.app_id}")
    private String appId;

    @Value("${api.edamam.app_key}")
    private String appKey;

    public JSONObject getNutritionData(String ingr) {
        // 构建API请求URL
        String apiUrl = BASE_URL + "?app_id=" + appId + "&app_key=" + appKey + "&nutrition-type=cooking&ingr=" + ingr;

        // 发送GET请求
        HttpResponse response = HttpRequest.get(apiUrl).execute();

        // 解析响应JSON
        String responseBody = response.body();
        return JSONUtil.parseObj(responseBody);
    }

    public JSONObject getDailyData(String ingr,String imageurl) {
        // 构建API请求URL
        String apiUrl = BASE_URL + "?app_id=" + appId + "&app_key=" + appKey + "&nutrition-type=cooking&ingr=" + ingr;

        // 发送GET请求
        HttpResponse response = HttpRequest.get(apiUrl).execute();

        // 解析响应JSON
        String responseBody = response.body();
        JSONObject jsonResponse = JSONUtil.parseObj(responseBody);

        // 获取特定营养数据
        JSONObject totalNutrients = jsonResponse.getJSONObject("totalNutrients");
        JSONObject carbohydrate = totalNutrients.getJSONObject("CHOCDF");
        JSONObject vitamin = totalNutrients.getJSONObject("VITB6A");
        JSONObject fat = totalNutrients.getJSONObject("FAT");
        JSONObject protein = totalNutrients.getJSONObject("PROCNT");

        // 获取卡路里值
        JSONObject totalNutrientsKCal = jsonResponse.getJSONObject("totalNutrientsKCal");
        JSONObject calories = totalNutrientsKCal.getJSONObject("ENERC_KCAL");

        // 获取各个营养的值和单位
        double carbohydrateValue = roundToTwoDecimalPlaces(carbohydrate.getDouble("quantity"));
        String carbohydrateUnit = StrUtil.emptyToDefault(carbohydrate.getStr("unit"), ""); // 获取单位并处理空字符串

        double vitaminValue = roundToTwoDecimalPlaces(vitamin.getDouble("quantity"));
        String vitaminUnit = StrUtil.emptyToDefault(vitamin.getStr("unit"), ""); // 获取单位并处理空字符串

        double fatValue = roundToTwoDecimalPlaces(fat.getDouble("quantity"));
        String fatUnit = StrUtil.emptyToDefault(fat.getStr("unit"), ""); // 获取单位并处理空字符串

        double proteinValue = roundToTwoDecimalPlaces(protein.getDouble("quantity"));
        String proteinUnit = StrUtil.emptyToDefault(protein.getStr("unit"), ""); // 获取单位并处理空字符串

        double caloriesValue = roundToTwoDecimalPlaces(calories.getDouble("quantity"));
        String caloriesUnit = StrUtil.emptyToDefault(calories.getStr("unit"), ""); // 获取单位并处理空字符串

        // 创建包含营养数据和单位的JSONObject
        JSONObject nutritionData = new JSONObject();
        nutritionData.put("carbohydrate", carbohydrateValue + " " + carbohydrateUnit);
        nutritionData.put("vitamin", vitaminValue + " " + vitaminUnit);
        nutritionData.put("fat", fatValue + " " + fatUnit);
        nutritionData.put("protein", proteinValue + " " + proteinUnit);
        nutritionData.put("calories", caloriesValue + " " + caloriesUnit);
        nutritionData.put("foodname", ingr.substring(2));
        nutritionData.put("imageurl",imageurl);
        return nutritionData;
    }

    private Double roundToTwoDecimalPlaces(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
