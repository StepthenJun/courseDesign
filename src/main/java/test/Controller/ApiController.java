package test.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import test.Service.ApiCallService;
import org.springframework.beans.factory.annotation.Autowired;
import test.entity.User;
import test.util.DemoTianAPI;
import test.util.SMSUtils;
import test.util.ValidateCodeUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    private  ApiCallService apiCallService;

    @GetMapping()
    public String callApi(@RequestParam String city) {
        return ApiCallService.queryWeather(city);
    }
    @GetMapping("/food")
    public String foodApi(@RequestParam String imgurl) throws Exception {
        return DemoTianAPI.apitest(imgurl);
    }
    @PostMapping("/sendMsg")
    public String sendMsg(@RequestBody User user, HttpSession session) throws Exception {
//      1.获取手机号
        String phone = user.getPhone();
        if(StringUtils.isEmpty(phone)){
            return "短信发送失败";
        }
//      2.随机生成四位验证码
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
//      3.调用阿里云提供的短信服务
        SMSUtils.sendMessage(code,phone);
//      4.需要将生成的验证码保存到session中
        session.setAttribute(phone,code);
        Object attribute = session.getAttribute(phone);
        return "验证码短信发送成功" + code;
    }
    //  其实传过来的phone:xxxx，code:xxx 也可以用map集合接收
    @PostMapping("/login")
    public String login(@RequestBody Map map, HttpSession session){
//      1. 获取手机号
        String phone = (String) map.get("phone");
//      2. 获取验证码
        String code = map.get("code").toString();
//      3. 从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
//      4. 进行验证码比对(页面提交的验证码和Session中保存的验证码比对)
        if (codeInSession != null && codeInSession.equals(code)) {
            System.out.println("拿到了");
//          5.对比成功，说明登录成功
            /*LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user==null){
//          6. 如果新用户，自动注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);*/
            return "success";
        }
        return "登录失败";
    }
}
