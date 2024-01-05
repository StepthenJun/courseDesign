package com.foodhealth.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.RegisterFormDTO;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.entity.UserInfo;
import com.foodhealth.service.UserInfoService;
import com.foodhealth.service.UserService;
import com.foodhealth.utils.*;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.User;
import com.foodhealth.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private CodeAndTime cat;

    @Autowired
    private UserInfoService uis;

    @Override
    public Result sendcode(String phone, HttpSession session) throws Exception {
//        判断是否为空
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式有问题");
        }
//        生成随机验证码
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
//        发送验证码
        SMSUtils.sendMessage(code, phone);
//        创建包含验证码和时间戳的对象
        cat = new CodeAndTime(code, System.currentTimeMillis());
//        把验证码和手机号放到session里
        session.setAttribute(phone, code);
        log.info("返回验证码成功，验证码为：{}", code);
        return Result.ok("验证码是" + code);
    }

    @Override
    public Result verify(RegisterFormDTO rfd, HttpSession session) {
//        先验证验证码格式
        String code = rfd.getCode();
        String phone = rfd.getPhone();

        if (RegexUtils.isCodeInvalid(code)) {
            return Result.fail("验证码格式错误");
        }
//        拿到发送的验证码
        Object catchCode = session.getAttribute(phone);

        if (catchCode == null || !catchCode.equals(code)) {
//            不一致就报错
            return Result.fail("验证码错误！");
        }
//        验证码是否在60秒内
        long timeElapsed = System.currentTimeMillis() - cat.getTimeStamp();
        if (timeElapsed > 60000L) {
//             60000 毫秒 = 60 秒
            return Result.fail("验证码已过期！");
        }
        log.info("登录成功");
        return Result.ok();
    }


    @Override
    public Result register(User user) {
//        TODO 密码不能为空
//        先判断这个手机号有没有注册过
        String phone = user.getPhone();
//        获取该手机号的用户
        User ifuser = query().eq("phone", phone).one();
        if (ifuser != null) {
//           如果有就返回已经被注册
            return Result.fail("该手机号已被注册");
        } else {
            uis.save(BeanUtil.copyProperties(user,UserInfo.class));
//            如果没有就创建一个对象
            createUserWithPhone(user);
        }
        return Result.ok("注册成功");
    }


    @Override
    public Result login(LoginFormDTO loginFormDTO) {
//        获取当前的手机号和密码
        String phone = loginFormDTO.getPhone();
        String password = loginFormDTO.getPassword();
//        去数据库做匹配
        if (RegexUtils.isPhoneInvalid(phone)) {
//            判断手机号格式是否正确
            return Result.fail("手机号格式错误");
        }
//        去找数据库中有没有注册过的手机号
        User user = query().eq("phone", phone).one();
        if (user == null) {
            return Result.fail("该用户还没注册过，请先去注册");
        }
        if (password != null && Objects.equals(password, user.getPassword())) {
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            log.info(userDTO.toString());
            String token = MyJwtUtil.createToken(userDTO);
            return Result.ok(token);
        } else {
            return Result.fail("密码错误");
        }
    }


    private User createUserWithPhone(User user) {
        User insertuser = new User();
        insertuser.setPhone(user.getPhone());
        if (user.getUsername() == null) {
//            账号的昵称为随机（如果没自定义）
            user.setUsername("user_" + RandomUtil.randomString(10));
        }else {
            insertuser.setUsername(user.getUsername());
        }
        save(user);
        return user;
    }
}
