package com.foodhealth.api;

import java.net.*;
import java.io.*; 
public class DemoTianAPI{ 
  public static String apitest(String img)throws Exception {
    String tianapi_data = "";
      String encode = URLEncoder.encode(img, "UTF-8");
      try {
        URL url = new URL( "https://apis.tianapi.com/imgnutrient/index");
        HttpURLConnection  conn = (HttpURLConnection) url.openConnection(); 
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        OutputStream outputStream = conn.getOutputStream();
        String content = "key=dde413587d168099d1938505cbc47bd6&img=" + encode ;
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader (inputStream,"utf-8");
        BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
        StringBuilder tianapi = new StringBuilder();
        String temp = null;
            while ( null != (temp = bufferedReader.readLine())){
            tianapi.append(temp);
        }
        tianapi_data = tianapi.toString();
        inputStream.close();
     } catch (IOException e) { 
        e.printStackTrace();
      }
      return tianapi_data;
}
}