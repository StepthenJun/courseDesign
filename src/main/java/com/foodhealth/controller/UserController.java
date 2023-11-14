package com.foodhealth.controller;


import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.RegisterFormDTO;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.User;
import com.foodhealth.entity.UserInfo;
import com.foodhealth.service.UserInfoService;
import com.foodhealth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("code")
    public Result sendcode(@RequestParam("phone") String phone,HttpSession session) throws Exception {
        return userservice.sendcode(phone,session);
    }

    @PostMapping("verify")
    public Result verify(@RequestBody RegisterFormDTO registerFormDTO, HttpSession session){
        return userservice.verify(registerFormDTO,session);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userservice.register(user);
    }

    @PostMapping("/refineInfo/{id}")
    public Result refineInfo(@PathVariable Long id, @RequestBody UserInfo userInfo){
        return userInfoService.refineInfo(id,userInfo);
    }
    @PostMapping("/login")
    private Result login(@RequestBody LoginFormDTO loginFormDTO,HttpSession session){
        return userservice.login(loginFormDTO,session);
    }

    @GetMapping
    public String s(){
        return "你好";
    }
}
