package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Article;
import com.foodhealth.entity.UserFollow;

import java.util.List;

public interface UserFollowService extends IService<UserFollow> {

    Result liked(Long communityId);

    Result collected(Long articleId);

    Long getLikesNum();
    Long getCollectsNum();

    List<Article> getLikes();
    List<Article> getCollects();
}
