package com.foodhealth.entity;


import com.foodhealth.dto.LoginFormDTO;
import com.foodhealth.dto.UserDTO;

public class UserHolder {
    private static final ThreadLocal<LoginFormDTO> tl = new ThreadLocal<>();

    public static void saveUser(LoginFormDTO user){
        tl.set(user);
    }

    public static LoginFormDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
