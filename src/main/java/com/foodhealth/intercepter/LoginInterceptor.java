package com.foodhealth.intercepter;

import cn.hutool.core.util.StrUtil;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object attribute = request.getSession().getAttribute("token");
        String token = (String) attribute;
        System.out.println(token);
        if (JwtUtil.verifyToken(token) || StrUtil.isBlank(token)){
            return true;
        }
        if (UserHolder.getUser() == null){
            response.setStatus(401);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
