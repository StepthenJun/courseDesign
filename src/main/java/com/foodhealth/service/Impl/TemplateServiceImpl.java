package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.*;
import com.foodhealth.mapper.TemplateMapper;
import com.foodhealth.service.TemplateService;
import com.foodhealth.service.UserFollowTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.foodhealth.constants.SystemConstants.MAX_PAGE_SIZE;

@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

    @Autowired
    private UserFollowTemplateService ufts;

    @Override
    public Result getTemplates() {
        List<Template> templates = query().list();
        templates.forEach(
                template -> {
                    this.isLiked(template);
                    this.isCollectd(template);
                }
        );
        return Result.ok(sort(templates));
    }

    public List<Template> sort(List<Template> templates){
        Collections.sort(templates, new Comparator<Template>() {
            @Override
            public int compare(Template o1, Template o2) {
                return o1.getLikedNum().compareTo(o2.getLikedNum());
            }
        });
        return templates;
    }

    private void isLiked(Template template) {
        UserDTO user = UserHolder.getUser();
        if (user == null){
            return;
        }
        Long userId = user.getId();
        Long id = template.getId();
        QueryWrapper<UserFollowTemplate> qw = new QueryWrapper<>();
        qw.eq("user_id", userId).eq("template_id", id);
        UserFollowTemplate one = ufts.getOne(qw);
        if (one == null){
            template.setIsLike(false);
            return;
        }
        Boolean like = one.getIsLike();
//        2.判断当前用户是否点赞
        template.setIsLike(like);
    }
    private void isCollectd(Template template) {
        UserDTO user = UserHolder.getUser();
        if (user == null){
            return;
        }
        Long userId = user.getId();
        Long id = template.getId();
        QueryWrapper<UserFollowTemplate> qw = new QueryWrapper<>();
        UserFollowTemplate one = ufts.getOne(qw.eq("user_id", userId).eq("template_id", id));
        if (one == null){
            template.setIsColloct(false);
            return;
        }
        Boolean collect = one.getIsCollect();
//        2.判断当前用户是否点赞
        template.setIsColloct(collect);
    }

}
