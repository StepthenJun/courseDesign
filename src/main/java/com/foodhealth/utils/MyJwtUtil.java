package com.foodhealth.utils;
 

import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.foodhealth.dto.UserDTO;
import com.foodhealth.entity.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
/**
 * @description: Jwt工具类，生成JWT和认证
 */
@Slf4j
public class MyJwtUtil {
 
    private static final Logger logger = LoggerFactory.getLogger(MyJwtUtil.class);
    /**
     * 密钥
     */
    private static final String KEY = "ywj";
 
    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 1800000L;//单位为秒
 
    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(UserDTO userDTO) {
        //过期时间
        DateTime now = DateTime.now();
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 96);
        Map<String, Object> payload  = new HashMap<>();
//        签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
//        生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
//        过期时间
        payload.put(JWTPayload.EXPIRES_AT, expireDate);
        payload.put("id", userDTO.getId());
        payload.put("username",userDTO.getUsername());
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

    //判断Token是否快过期了
    public static boolean isTokenCloseToExpiry(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            Date expiration = (Date) jwt.getPayload(JWTPayload.EXPIRES_AT);
            long timeToExpiry = expiration.getTime() - System.currentTimeMillis();
            // 假设如果Token剩余有效期小于10分钟，则需要刷新
            return timeToExpiry < 10 * 60 * 1000;
        } catch (Exception e) {
            // 解析失败，认为需要刷新
            return true;
        }
    }

    // 刷新Token
    public static String refreshToken(String oldToken) {
        // 解析旧Token
        JWT jwt = JWTUtil.parseToken(oldToken);
        UserDTO userDTO = UserHolder.getUser();
        return createToken(userDTO);
    }

    //解析token拿到token里的用户信息
    /**
     * 解析Token并获取用户信息
     * @param token JWT Token
     * @return LoginFormDTO 包含用户信息的对象
     */
    public static UserDTO parseToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        if (!verifyToken(token)) {
            throw new IllegalStateException("Token verification failed");
        }
        // 获取payload中的信息
        Long id = ((Integer)jwt.getPayload("id")).longValue();
        String username = (String) jwt.getPayload("username");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id).setUsername(username);
        log.info("userDTO:{}",userDTO);
        return userDTO;
    }

}