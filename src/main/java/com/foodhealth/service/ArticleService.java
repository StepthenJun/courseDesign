package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Article;

public interface ArticleService extends IService<Article> {
    Result getarticles();

    Result skim(Long id);


    Result getDataByCondition(String keyWord);
}
