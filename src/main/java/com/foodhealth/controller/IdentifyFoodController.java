package com.foodhealth.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.foodhealth.api.DemoTianAPI;
import com.foodhealth.api.FoodNutritionAPI;
import com.foodhealth.constants.SystemConstants;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Template;
import com.foodhealth.service.TemplateService;
import com.foodhealth.utils.GetBase64Util;
import com.foodhealth.utils.TranslateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RequestMapping("/identify")
@RestController
@Slf4j
@Api(tags ="扫描识别的接口")
public class IdentifyFoodController {
    @Autowired
    private FoodNutritionAPI foodNutritionAPI;

    @Autowired
    private TemplateService templateService;
/*    @PostMapping
    public String identifyfood(@RequestParam("imagefile") MultipartFile imagefile) throws Exception {
        return DemoTianAPI.apitest(GetBase64Util.getbase(imagefile));
    }

    @PostMapping("/new")
    public JSONObject idengtify(@RequestParam String foodName){
        return foodNutritionAPI.getNutritionData(foodName);
    }

    @PostMapping("/getdaily")
    public JSONObject getdaily(@RequestParam String foodName){
        return foodNutritionAPI.getDailyData(foodName);
    }*/

//    根据食物识别返回食物的数据(识别和记录都能用,修改数据就好了)

    @ApiOperation("扫描食物返回数据")
    @PostMapping()
    public JSONObject finaltest(@RequestParam("imagefile") MultipartFile imagefile) throws Exception {
//        获取识别到的食物数据
        String identifyFoodName = DemoTianAPI.apitest(GetBase64Util.getbase(imagefile));
        System.out.println(identifyFoodName);
//        通过解析拿到识别度最高的食物
        JSONObject jsonResponse = new JSONObject(identifyFoodName);
        JSONArray list = jsonResponse.getJSONObject("result").getJSONArray("list");
        // 提取第一个元素的名称字段
        if (list != null && !list.isEmpty()) {
            String firstFoodName = list.getJSONObject(0).getStr("name");
            System.out.println("第一个食物的名称是: " + TranslateUtil.translate(firstFoodName));
            String translated = TranslateUtil.translate(firstFoodName);
            String image = uploadImage(imagefile);
            return foodNutritionAPI.getDailyData("a " + translated,image);
        } else {
            System.out.println("列表为空");
            return new JSONObject("识别错误");
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
        File dir = new File(SystemConstants.IMAGE_UPLOAD_DIR, StrUtil.format("/articles/{}/{}", d1, d2));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format("/articles/{}/{}/{}.{}", d1, d2, name, suffix);
    }

/*    @ApiOperation("上传模板")
    @PostMapping("/upload")
    public Result publish(@RequestBody Template template){
        templateService.save(template);
        return Result.ok();
    }*/
}
