package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.ArticelListDTO;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.Article;
import com.foodhealth.entity.Health;
import com.foodhealth.entity.UserFollow;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.mapper.ArticleMapper;
import com.foodhealth.service.ArticleService;
import com.foodhealth.service.RecordService;
import com.foodhealth.service.UserFollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserFollowService userFollowService;


    @Override
    public Result getarticles() {
        ArticelListDTO articelList = new ArticelListDTO();
        List<Article> recomendList = query().list();
        if (UserHolder.getUser() == null){
            articelList.setRecomendAticle(recomendList);
            return Result.ok(articelList);
        }
        else {
            recomendList.forEach(
                    article -> {
                        this.isLiked(article);
                        this.isCollectd(article);
                    }
            );
            articelList.setRecomendAticle(recomendList);
            List<Article> likes = userFollowService.getLikes();
            likes.forEach(
                    article -> {
                        this.isLiked(article);
                        this.isCollectd(article);
                    }
            );
            articelList.setFollowArticle(sort(likes));
        }
        return Result.ok(articelList);
    }
    @Override
    public Result skim(Long id) {
//        获取用户信息
        UserDTO userDTO = UserHolder.getUser();
        Article article = query().eq("id", id).one();
//        拿到用户的id
        if (userDTO == null){
            return Result.ok(article);
        }
        else {
            Long userId = userDTO.getId();
            recordService.record(userId);
            return Result.ok(article);
        }
    }

    @Override
    public Result getDataByCondition(String keyWord) {
        List<Article> articleList = query()
                .like("LOWER(title)", keyWord)
                .or()
                .like("LOWER(content)",keyWord)
                .list();
        return Result.ok(articleList);
    }

    public List<Article> sort(List<Article> likes){
        Collections.sort(likes, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getLikedNum().compareTo(o2.getLikedNum());
            }
        });
        return likes;
    }
    private void isLiked(Article article) {
        UserDTO user = UserHolder.getUser();
        if (user == null){
            return;
        }
        Long userId = user.getId();
        Long id = article.getId();
        QueryWrapper<UserFollow> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("article_id", id);
        UserFollow one = userFollowService.getOne(qw);
        if (one == null){
            article.setIsLike(false);
            return;
        }
        Boolean like = one.getIsLike();
//        2.判断当前用户是否点赞
        article.setIsLike(like);
    }
    private void isCollectd(Article article) {
        UserDTO user = UserHolder.getUser();
        if (user == null){
            return;
        }
        Long userId = user.getId();
        Long id = article.getId();
        QueryWrapper<UserFollow> qw = new QueryWrapper<>();
        UserFollow one = userFollowService.getOne(qw.eq("user_id", userId).eq("article_id", id));
        if (one == null){
            article.setIsColloct(false);
            return;
        }
        Boolean collect = one.getIsCollect();
//        2.判断当前用户是否点赞
        article.setIsColloct(collect);
    }




}
