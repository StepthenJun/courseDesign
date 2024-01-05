package com.foodhealth.intercepter;

import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.UserHolder;
import com.foodhealth.utils.MyJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许所有的 OPTIONS 请求通过
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String uri = request.getRequestURI();
        if (uri.startsWith("/images/")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            log.info("未提供 token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未授权：缺少认证信息");
            return false;
        }

        try {
            if (!MyJwtUtil.verifyToken(token)) {
                log.info("无效的 token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("未授权：无效的认证信息");
                return false;
            }
            UserDTO userDTO = MyJwtUtil.parseToken(token);
            UserHolder.saveUser(userDTO);
            return true;
        } catch (Exception e) {
            log.error("Token 解析异常", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
