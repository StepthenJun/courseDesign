package com.foodhealth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.User;
import com.foodhealth.entity.UserFollow;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.entity.UserInfo;
import com.foodhealth.mapper.UserInfoMapper;
import com.foodhealth.service.UserFollowService;
import com.foodhealth.service.UserInfoService;
import com.foodhealth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFollowService userFollowService;
    @Override
    public Result refineInfo(Long id, UserInfo userInfo) {
//        根据路径传的id获取已经注册的对象
        User user = userService.getById(id);
//        再通过获取的对象拿到昵称和id
        String username = user.getUsername();
        Long userId = user.getId();
//        将这些放入userInfo里
//        判断是不是为空，为空则加入，不为空就不注入了（模仿第一次进入资料编辑场景）
        UserInfo byId = getById(userId);
        if (byId == null){
            userInfo.setUserId(userId).setUsername(username);
            save(userInfo);
        }
//        不为空，就是进来更新资料的
        else update(userInfo,update().eq("user_id",userId));
        return Result.ok("更新成功");
    }

    @Override
    public Result getlikes() {
//        根据userfollow的表单查询文章id就好了
        return Result.ok(userFollowService.getLikes());
    }

    @Override
    public Result getcollects() {
//        根据userfollow的表单查询文章id就好了
        return Result.ok(userFollowService.getCollects());
    }

    @Override
    public Result getInfo() {
//        这个功能只返回名称，id，等级，喜欢的数量，收藏的数量
        Long userId = UserHolder.getUser().getId();
        UserInfo userInfo = query().eq("user_id", userId).one();
        Long likesNum = userFollowService.getLikesNum();
        Long collectsNum = userFollowService.getCollectsNum();
        userInfo.setLikesNum(likesNum);
        userInfo.setCollectNum(collectsNum);
        return Result.ok(userInfo);
    }
}
