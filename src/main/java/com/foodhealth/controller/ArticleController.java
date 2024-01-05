package com.foodhealth.controller;

import com.foodhealth.dto.Result;
import com.foodhealth.entity.Article;
import com.foodhealth.service.ArticleService;
import com.foodhealth.service.UserFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/article")
@Api(tags ="Article接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @Autowired
    private UserFollowService userFollowService;

    @ApiOperation("获取文章列表")
    @GetMapping("/getarticles")
    public Result getarticles() {
        return articleService.getarticles();
    }
    @ApiOperation("浏览文章")
    @GetMapping("/getarticles/{id}")
    public Result skim(@PathVariable("id")  Long id){
        return articleService.skim(id);
    }

    @ApiOperation("发表文章")
    @PostMapping("/publish")
    public Result publish(@RequestBody  Article article){
        articleService.save(article);
        return Result.ok();
    }

    @ApiOperation("点赞文章")
    @PostMapping("/like")
    public Result like(@RequestParam  Long articleId){
        return userFollowService.liked(articleId);
    }

    @ApiOperation("收藏文章")
    @PostMapping("/collect")
    public Result collect(@RequestParam  Long articleId){
        return userFollowService.collected(articleId);
    }

    @ApiOperation("article界面搜索数据")
    @GetMapping("/search")
    public Result getDataByCondition(@RequestParam  String keyWord){
        return articleService.getDataByCondition(keyWord);
    }
}
