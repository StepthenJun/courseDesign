package com.foodhealth.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.foodhealth.api.DemoTianAPI;
import com.foodhealth.api.FoodNutritionAPI;
import com.foodhealth.constants.SystemConstants;
import com.foodhealth.dto.Result;
import com.foodhealth.service.HealthService;
import com.foodhealth.utils.GetBase64Util;
import com.foodhealth.utils.TranslateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/uploadMeal")
@Api(tags ="上传每日用餐的接口")
public class UploadDailyMealController {

    @Autowired
    private FoodNutritionAPI foodNutritionAPI;

    @Autowired
    private HealthService healthService;

    @PostMapping
    public Result uploadMeal(@RequestParam("imagefile")  MultipartFile imagefile) throws Exception{
        //        获取识别到的食物数据
        String identifyFoodName = DemoTianAPI.apitest(GetBase64Util.getbase(imagefile));
//        通过解析拿到识别度最高的食物
        JSONObject jsonResponse = new JSONObject(identifyFoodName);
        JSONArray list = jsonResponse.getJSONObject("result").getJSONArray("list");
        // 提取第一个元素的名称字段
        if (list != null && !list.isEmpty()) {
            String firstFoodName = list.getJSONObject(0).getStr("name");
            System.out.println("第一个食物的名称是: " + firstFoodName);
            String translated = TranslateUtil.translate(firstFoodName);
            System.out.println(translated);
            String imgurl = uploadImage(imagefile);
            JSONObject dailyData = foodNutritionAPI.getDailyData("a " + translated,imgurl);
            return healthService.uploadMeal(dailyData,translated,imgurl);
        } else {
            System.out.println("列表为空");
            return Result.fail("识别失败");
        }
    }

    public String uploadImage(MultipartFile image) {
        try {
            // 获取原始文件名称
            String originalFilename = image.getOriginalFilename();
            // 生成新文件名
            String fileName = createNewFileName(originalFilename);
            // 保存文件
            image.transferTo(new File(SystemConstants.IMAGE_UPLOAD_DIR, fileName));
            // 返回结果
            log.debug("文件上传成功，{}", fileName);
            return SystemConstants.IMAGE_UPLOAD_DIR + fileName;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    private String createNewFileName(String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();
        int hash = name.hashCode();
        int d1 = hash & 0xF;
        int d2 = (hash >> 4) & 0xF;
        // 判断目录是否存在
        File dir = new File(SystemConstants.IMAGE_UPLOAD_DIR, StrUtil.format("/foodlist/{}/{}", d1, d2));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format("/foodlist/{}/{}/{}.{}", d1, d2, name, suffix);
    }

}
