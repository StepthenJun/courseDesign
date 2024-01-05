package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.UserFollowTemplate;
public interface UserFollowTemplateService extends IService<UserFollowTemplate> {
    Result liked(Long templateId);

    Result collected(Long templateId);

    Long getLikesNum();
    Long getCollectsNum();
}
