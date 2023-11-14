package com.foodhealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.RegisterFormDTO;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.User;
import com.foodhealth.entity.UserInfo;


import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    Result sendcode(String phone, HttpSession session) throws Exception;

    Result register(User user);

    Result verify(RegisterFormDTO registerFormDTO, HttpSession session);

    Result login(LoginFormDTO loginFormDTO,HttpSession session);
}
