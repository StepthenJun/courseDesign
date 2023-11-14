package com.foodhealth.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetBase64Util {
    public static String getbase(MultipartFile img){
        byte[] data = null;
        try {
            data = img.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64Util encoder = new Base64Util();
        return encoder.encode(data);
    }
}
