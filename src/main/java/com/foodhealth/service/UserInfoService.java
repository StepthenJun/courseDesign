package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    Result refineInfo(Long id, UserInfo userInfo);
}
