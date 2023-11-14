package com.foodhealth.controller;


import com.foodhealth.Api.DemoTianAPI;
import com.foodhealth.utils.GetBase64Util;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/identify")
@RestController
@Api("扫描识别的接口")
public class IdentifyFoodController {

    @PostMapping
    public String identifyfood(@RequestParam("imagefile") MultipartFile imagefile) throws Exception {
        return DemoTianAPI.apitest(GetBase64Util.getbase(imagefile));
    }
}
