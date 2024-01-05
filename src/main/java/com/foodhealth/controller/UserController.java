package com.foodhealth.controller;


import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.RegisterFormDTO;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.Health;
import com.foodhealth.entity.User;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.entity.UserInfo;
import com.foodhealth.service.HealthService;
import com.foodhealth.service.UserInfoService;
import com.foodhealth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags ="关于用户操作的接口")
public class UserController {

    @Autowired
    private UserService userservice;
    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation("发送短信")
    @PostMapping("code")
    public Result sendcode(@RequestParam("phone")String phone,
                           HttpSession session) throws Exception {
        return userservice.sendcode(phone,session);
    }

    @ApiOperation("验证短信")
    @PostMapping("verify")
    public Result verify(@RequestBody RegisterFormDTO registerFormDTO,
                         HttpSession session){
        return userservice.verify(registerFormDTO,session);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userservice.register(user);
    }

    @ApiOperation("在个人主页里修改个人资料")
    @PostMapping("/ofme/refineInfo/{id}")
    public Result refineInfo(@PathVariable Long id,
                             @RequestBody UserInfo userInfo){
        return userInfoService.refineInfo(id,userInfo);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    private Result login(@RequestBody LoginFormDTO loginFormDTO){
        return userservice.login(loginFormDTO);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result logout() {
        // 清除UserHolder中的用户信息
        UserHolder.removeUser();
        // 返回登出成功的消息
        return Result.ok("成功退出");
    }

    @ApiOperation("Me界面信息")
    @GetMapping("/ofme")
    public Result getUserInfo(){
        return userInfoService.getInfo();
    }

    @ApiOperation("Me界面查看喜欢列表")
    @GetMapping("/ofme/getlikes")
    public Result getlikes(){
        return userInfoService.getlikes();
    }

    @ApiOperation("Me界面查看收藏列表")
    @GetMapping("/ofme/getcollects")
    public Result getcollects(){
        return userInfoService.getcollects();
    }

    @GetMapping("彩蛋")
    public String s(){
        return "你好";
    }
}
