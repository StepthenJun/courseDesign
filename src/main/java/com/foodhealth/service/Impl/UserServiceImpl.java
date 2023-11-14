package com.foodhealth.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.RegisterFormDTO;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.entity.UserInfo;
import com.foodhealth.service.UserService;
import com.foodhealth.utils.JwtUtil;
import com.foodhealth.utils.RegexUtils;
import com.foodhealth.dto.Result;
import com.foodhealth.entity.User;
import com.foodhealth.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.foodhealth.utils.SMSUtils;
import com.foodhealth.utils.ValidateCodeUtils;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Override
    public Result sendcode(String phone, HttpSession session) throws Exception {
//        判断是否为空
        if(RegexUtils.isPhoneInvalid(phone)){
            return Result.fail("手机号格式有问题");
        }
//        生成随机验证码
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
//        发送验证码
        SMSUtils.sendMessage(code,phone);
//        把验证码和手机号放到session里
        session.setAttribute(phone,code);
        log.info("返回验证码成功，验证码为：{}",code);
        return Result.ok("验证码是" + code);
    }

    @Override
    public Result verify(RegisterFormDTO rfd,HttpSession session) {
//        先验证验证码格式
        String code = rfd.getCode();
        String phone = rfd.getPhone();
        if (RegexUtils.isCodeInvalid(code)){
            return Result.fail("验证码格式错误");
        }
//        拿到发送的验证码
        Object catchCode = session.getAttribute(phone);
        if (catchCode == null || !catchCode.equals(code)){
//            不一致就报错
            return Result.fail("验证码错误！");
        }
        return Result.ok();
    }


    @Override
    public Result register(User user) {
//        TODO 密码不能为空
//        先判断这个手机号有没有注册过
        String phone = user.getPhone();
//        获取该手机号的用户
        User ifuser = query().eq("phone", phone).one();
        if (ifuser != null){
//           如果有就返回已经被注册
            return Result.fail("该手机号已被注册");
        }else {
//            如果没有就创建一个对象
            createUserWithPhone(phone);
        }
        return Result.ok("注册成功");
    }


    @Override
    public Result login(LoginFormDTO loginFormDTO,HttpSession session) {
//        获取当前的手机号和密码
        String phone = loginFormDTO.getPhone();
        String password = loginFormDTO.getPassword();
//        去数据库做匹配
        if (RegexUtils.isPhoneInvalid(phone)){
//            判断手机号格式是否正确
            return Result.fail("手机号格式错误");
        }
//        去找数据库中有没有注册过的手机号
        User user = query().eq("phone", phone).one();
        if (user == null){
            return Result.fail("该用户还没注册过，请先去注册");
        }

        if (password != null && password.equals(user.getPassword())){
            String token = JwtUtil.createToken(loginFormDTO);
            session.setAttribute("token",token);
            UserHolder.saveUser(loginFormDTO);
            return Result.ok("登录成功");
        }
        else return Result.fail("密码错误");
    }


    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        if (user.getUsername() == null) {
//            账号的昵称为随机（如果没自定义）
            user.setUsername("user_" + RandomUtil.randomString(10));
        }
        save(user);
        return user;
    }


}
