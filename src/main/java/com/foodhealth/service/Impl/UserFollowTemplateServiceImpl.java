package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.UserFollowTemplate;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.mapper.UserFollowTemplateMapper;
import com.foodhealth.service.TemplateService;
import com.foodhealth.service.UserFollowTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserFollowTemplateServiceImpl extends ServiceImpl<UserFollowTemplateMapper, UserFollowTemplate> implements UserFollowTemplateService {
    @Autowired
    private TemplateService templateService;

    @Override
    public Result liked(Long templateId) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        return ifliked(userId,templateId);
    }

    @Override
    public Result collected(Long templateId) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        return ifcollected(userId,templateId);
    }

    @Override
    public Long getLikesNum() {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        Integer likesCount = query().eq("user_id", userId)
                .eq("is_like", true)
                .count();
        return likesCount.longValue();
    }

    @Override
    public Long getCollectsNum() {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        Integer likesCount = query().eq("user_id", userId)
                .eq("is_collect", true)
                .count();
        return likesCount.longValue();
    }
    private Result ifliked(Long userId,Long templateId) {
//        1.判断是否点赞过这个文章根据userid在userfollow表中
        UserFollowTemplate userFollow = query()
                .eq("user_id", userId)
                .eq("template_id",templateId)
                .one();
//        2.如果不存在
        if (userFollow == null){
//            2.1创建这个数据
            userFollow = saveUser(userId, templateId);
        }
//        获取是否收藏这个文章
        assert userFollow != null;
        Boolean isLike = userFollow.getIsLike();
        if (isLike){
            Long id = userFollow.getId();
            log.info("id:{}",id);
            update().eq("id",id)
                    .set("is_like",false)
                    .update();
            templateService.update().setSql("liked_num = liked_num - 1")
                    .eq("id",id).update();
            return Result.ok("取消点赞");
        }
        else {
            Long id = userFollow.getId();
            update().eq("id",id)
                    .set("is_like",true)
                    .update();
            templateService.update().setSql("liked_num = liked_num + 1")
                    .eq("id",id).update();
            return Result.ok("成功点赞");
        }
    }
    private Result ifcollected(Long userId,Long templateId) {
//        1.判断是否点赞过这个文章根据userid在userfollow表中
        UserFollowTemplate userFollow = query()
                .eq("user_id", userId)
                .eq("template_id",templateId)
                .one();
//        2.如果不存在
        if (userFollow == null){
//            2.1创建这个数据
            userFollow = saveUser(userId, templateId);
        }
//        获取是否收藏这个文章
        assert userFollow != null;
        Boolean isCollect = userFollow.getIsCollect();
        if (isCollect){
            Long id = userFollow.getId();
            update().eq("id",id)
                    .set("is_collect",false)
                    .update();
            templateService.update().setSql("collected_num = collected_num - 1")
                    .eq("id",id).update();
            return Result.ok("取消收藏");
        }
        else {
            Long id = userFollow.getId();
            update().eq("id",id)
                    .set("is_collect",true)
                    .update();
            templateService.update().setSql("collected_num = collected_num + 1")
                    .eq("id",id).update();
            return Result.ok("成功收藏");
        }
    }

    private UserFollowTemplate saveUser(Long userId,Long templateId){
        UserFollowTemplate user = new UserFollowTemplate()
                .setUserId(userId)
                .setTemplateId(templateId);
        save(user);
        return user;
    }
}
