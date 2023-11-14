package com.foodhealth.utils;
 

import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
/**
 * @description: Jwt工具类，生成JWT和认证
 */
public class JwtUtil {
 
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 密钥
     */
    private static final String KEY = "ywj";
 
    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 1800L;//单位为秒
 
    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(LoginFormDTO loginFormDTO) {
        //过期时间
        DateTime now = DateTime.now();
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> payload  = new HashMap<>();
//        签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
//        生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
//        过期时间
        payload.put(JWTPayload.EXPIRES_AT, expireDate);
        payload.put("phone", loginFormDTO.getPhone());
        payload.put("passWord",loginFormDTO.getPassword());
        String token = JWTUtil.createToken(payload, KEY.getBytes());
        return token;
    }
 
    /**
     * 校验token并解析token
     */
    public static Boolean verifyToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        boolean verify = jwt.setKey(KEY.getBytes()).verify();
        boolean validate = jwt.validate(0);
        return verify && validate;
    }
 
}