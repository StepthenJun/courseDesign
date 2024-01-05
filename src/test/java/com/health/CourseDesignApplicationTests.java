package com.health;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.foodhealth.CourseDesignApplication;
import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.Result;
import com.foodhealth.dto.UserDTO;

import com.foodhealth.entity.UserHolder;
import com.foodhealth.mapper.PstMapper;
import com.foodhealth.mapper.UserInfoMapper;
import com.foodhealth.mapper.UserMapper;
import com.foodhealth.service.ArticleService;
import com.foodhealth.service.Impl.PstServiceImpl;
import com.foodhealth.service.Impl.UserFollowServiceImpl;
import com.foodhealth.service.Impl.UserInfoServiceImpl;
import com.foodhealth.service.Impl.UserServiceImpl;
import com.foodhealth.service.UserFollowService;
import com.foodhealth.service.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = CourseDesignApplication.class)
class CourseDesignApplicationTests {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PstMapper pstMapper;
    @Autowired
    PstServiceImpl pstService;
    @Autowired
    UserFollowServiceImpl ufs;
    @Autowired
    UserInfoMapper uim;
    @Autowired
    UserHolder userHolder;
    @Autowired
    UserInfoServiceImpl uisi;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginWithInvalidPhone() {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setPhone("invalidPhone");
        loginFormDTO.setPassword("password");

        Result result = userService.login(loginFormDTO);

        Assertions.assertEquals("手机号格式错误", result.getErrorMsg());
    }

    @Test
    public void testLoginWithUnregisteredUser() {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setPhone("18522240236");
        loginFormDTO.setPassword("password");

        Result result = userService.login(loginFormDTO);

        Assertions.assertEquals("该用户还没注册过，请先去注册", result.getErrorMsg());
    }
    @Test
    public void testLoginWithWrongPassword() {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setPhone("18750851691");
        loginFormDTO.setPassword("wrongPassword");

        Result result = userService.login(loginFormDTO);

        Assertions.assertEquals("密码错误", result.getErrorMsg());
    }




}
