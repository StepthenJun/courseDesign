package com.foodhealth.controller;


import com.foodhealth.dto.Result;
import com.foodhealth.entity.Article;
import com.foodhealth.entity.Template;
import com.foodhealth.service.TemplateService;
import com.foodhealth.service.UserFollowTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/template")
@Api(tags ="模板的接口")
public class TemplateController {

    @Autowired
    private TemplateService templateService;
    @Autowired
    private UserFollowTemplateService ufts;

    @ApiOperation("获取模板列表")
    @GetMapping
    private Result getTemplates(){
        return templateService.getTemplates();
    }

    @ApiOperation("点赞模板")
    @PostMapping("/like")
    public Result like(@RequestParam Long templateId){
        return ufts.liked(templateId);
    }

    @ApiOperation("收藏文章")
    @PostMapping("/collect")
    public Result collect(@RequestParam  Long templateId){
        return ufts.collected(templateId);
    }


}
