package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.Article;
import com.foodhealth.entity.UserFollow;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.mapper.UserFollowMapper;
import com.foodhealth.service.ArticleService;
import com.foodhealth.service.UserFollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

    @Autowired
    private ArticleService articleService;

    @Override
    public Result liked(Long articleId) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        return ifliked(userId,articleId);
    }

    @Override
    public Result collected(Long articleId) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        return ifcollected(userId,articleId);
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

    @Override
    public List<Article> getLikes(){
        Long id = UserHolder.getUser().getId();
        List<UserFollow> userFollows = query()
                .eq("user_id", id)
                .eq("is_like",1)
                .list();
        List<Article> list = articleService.list();
        if (userFollows == null){
            return null;
        }
        System.out.println("什么"+userFollows);
        List<Article> articleList = new ArrayList<>();
        for (Article article : list) {
            for (UserFollow userFollow : userFollows) {
                if (userFollow.getArticleId().equals(article.getId())){
                    articleList.add(article);
                }
            }
        }
        System.out.println(articleList);
        return articleList;
    }

    @Override
    public List<Article> getCollects(){
        Long id = UserHolder.getUser().getId();
        List<Article> list = articleService.list();
        List<UserFollow> userFollows = query()
                .eq("user_id", id)
                .eq("is_collect",1)
                .list();
        if (userFollows == null){
            return null;
        }
        List<Article> articleList = new ArrayList<>();
        for (Article article : list) {
            for (UserFollow userFollow : userFollows) {
                if (userFollow.getArticleId().equals(article.getId())){
                    articleList.add(article);
                }
            }
        }
        return articleList;
    }

    private Result ifliked(Long userId,Long articleId) {
//        1.判断是否点赞过这个文章根据userid在userfollow表中
        UserFollow userFollow = query()
                .eq("user_id", userId)
                .eq("article_id",articleId)
                .one();
//        2.如果不存在
        if (userFollow == null){
//            2.1创建这个数据
            userFollow = saveUser(userId, articleId);
            update().set("is_like",true)
                    .eq("id",userFollow.getId())
                    .update();
            return Result.ok("收藏成功");
        }else {
//        获取是否点赞这个文章
            Boolean isLike = userFollow.getIsLike();
            if (isLike) {
                Long id = userFollow.getId();
                log.info("id:{}", id);
                update().eq("id", id)
                        .set("is_like", false)
                        .update();
                articleService.update().setSql("liked_num = liked_num - 1")
                        .eq("id", id).update();
                return Result.ok("取消点赞");
            } else {
                Long id = userFollow.getId();
                update().eq("id", id)
                        .set("is_like", true)
                        .update();
                articleService.update().setSql("liked_num = liked_num + 1")
                        .eq("id", id).update();
                return Result.ok("成功点赞");
            }
        }
    }
    private Result ifcollected(Long userId,Long articleId) {
//        1.判断是否点赞过这个文章根据userid在userfollow表中
        UserFollow userFollow = query()
                .eq("user_id", userId)
                .eq("article_id",articleId)
                .one();
//        2.如果不存在
        if (userFollow == null){
//            2.1创建这个数据
             userFollow = saveUser(userId, articleId);
             update().set("is_collect",true)
                     .eq("id",userFollow.getId())
                     .update();
             return Result.ok("收藏成功");
        }else {
//        获取是否收藏这个文章
            Boolean isCollect = userFollow.getIsCollect();
            if (isCollect) {
                Long id = userFollow.getId();
                update().eq("id", id)
                        .set("is_collect", false)
                        .update();
                articleService.update().setSql("collected_num = collected_num - 1")
                        .eq("id", id).update();
                return Result.ok("取消收藏");
            } else {
                Long id = userFollow.getId();
                update().eq("id", id)
                        .set("is_collect", true)
                        .update();
                articleService.update().setSql("collected_num = collected_num + 1")
                        .eq("id", id).update();
                return Result.ok("成功收藏");
            }
        }
    }

    private UserFollow saveUser(Long userId,Long articleId){
        UserFollow user = new UserFollow()
                .setUserId(userId)
                .setArticleId(articleId);
        save(user);
        return user;
    }
}
